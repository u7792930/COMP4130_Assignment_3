"""
Task 5 Pipeline: Feedback Loop (Compilation Repair + Behavior-Guided Improvement)
==================================================================================
This script implements the two-phase feedback loop required by Task 5:

  Phase A - Compilation Repair Loop (Runnability)
    1. Compile every generated test file with javac against the project's
       classpath.
    2. On failure, capture the compiler diagnostics and ask the LLM for a
       revised version (failing code + diagnostics).
    3. Repeat for up to 3 iterations (or stop earlier when the test compiles).

  Phase B - Behavior-Guided Improvement Loop (Effectiveness)
    1. Execute each compilable test under JUnit 4 (via JUnitCore) while a
       JaCoCo agent records coverage of the focal class.
    2. Collect behavioural feedback: runtime failures + uncovered lines /
       branches inside the focal method.
    3. Use that feedback to drive a targeted regeneration round; the new
       version then re-enters Phase A so it stays runnable.

Final artefact: the original task4_output.csv is rewritten as
task5_output.csv with a new column "Runnable Test Code" containing:
  - "Code After Formatting" verbatim, for tests that compile out of the box.
  - The final repaired/improved revision otherwise (or the last attempt
    if no version ever compiled).

Prerequisites:
    pip install openai pandas
    JDK 8 on PATH (the Defects4J projects require Java 8 because Compress
    uses java.util.jar.Pack200 which was removed in JDK 14).

Usage:
    python task5.py
"""

import openai
import pandas as pd
import re
import os
import sys
import time
import shutil
import logging
import tempfile
import subprocess
import xml.etree.ElementTree as ET
from pathlib import Path

# ============================================================================
# CONFIG
# ============================================================================

REPO_ROOT = Path(__file__).resolve().parents[2]

CONFIG = {
    # OpenAI API key (reused from Task 4)
    "api_key": "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",

    # I/O
    "input_csv":  str(REPO_ROOT / "LLM_Test_Gen" / "Data" / "task4_output.csv"),
    "output_csv": str(REPO_ROOT / "LLM_Test_Gen" / "Data" / "task5_output.csv"),

    # Column names
    "fqn_column": "FQN",
    "code_column": "Code After Formatting",
    "saved_path_column": "Saved Path",
    "runnable_column": "Runnable Test Code",

    # Defects4J project layout
    "project_roots": {
        "codec":       str(REPO_ROOT / "defects4jprojects" / "Codec_18_buggy"),
        "collections": str(REPO_ROOT / "defects4jprojects" / "Collections_27_buggy"),
        "compress":    str(REPO_ROOT / "defects4jprojects" / "Compress_45_buggy"),
    },
    "test_src_dirs": {
        "codec": "src/test/java",
        "collections": "src/test/java",
        "compress": "src/test/java",
    },
    "test_packages": {
        "codec":       "org.apache.commons.codec.binary",
        "collections": "org.apache.commons.collections4.map",
        "compress":    "org.apache.commons.compress.archivers.tar",
    },
    "fqn_to_project": {
        "commons.codec":        "codec",
        "commons.collections4": "collections",
        "commons.compress":     "compress",
    },

    # LLM settings
    "model": "gpt-4o-mini",
    "temperature": 0.3,    # lower temperature for repair (more deterministic)
    "max_tokens": 2048,
    "api_delay": 1.0,

    # Repair / behaviour loops
    "max_repair_iterations": 3,
    "max_behavior_iterations": 1,
    "test_run_timeout_sec": 60,

    # Java toolchain - JDK 8 is required for Compress (Pack200 removed in 14)
    "java_home": "/Users/tawhidlabib/Library/Java/JavaVirtualMachines/corretto-1.8.0_482/Contents/Home",

    # JUnit / Hamcrest jars (from local Maven cache)
    "junit_jar":    "/Users/tawhidlabib/.m2/repository/junit/junit/4.12/junit-4.12.jar",
    "hamcrest_jar": "/Users/tawhidlabib/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar",

    # JaCoCo (for Phase B coverage feedback)
    "jacoco_agent_jar": "/Users/tawhidlabib/.m2/repository/org/jacoco/org.jacoco.agent/0.8.8/org.jacoco.agent-0.8.8-runtime.jar",
    "jacoco_cli_jar":   "/Users/tawhidlabib/.m2/repository/org/jacoco/org.jacoco.cli/0.8.8/org.jacoco.cli-0.8.8-nodeps.jar",
}

# ============================================================================
# LOGGING
# ============================================================================

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(message)s",
    handlers=[
        logging.FileHandler(str(REPO_ROOT / "task5_pipeline.log")),
        logging.StreamHandler(),
    ],
)
logger = logging.getLogger(__name__)

# ============================================================================
# HELPERS
# ============================================================================

def resolve_project(fqn: str) -> str:
    """Determine which Defects4J project owns the focal method."""
    for substring, project in CONFIG["fqn_to_project"].items():
        if substring in fqn:
            return project
    return None


def focal_class_fqn(fqn: str) -> str:
    """org.foo.Bar.method -> org.foo.Bar"""
    return ".".join(fqn.split(".")[:-1])


def detect_class_name(code: str) -> str:
    m = re.search(r"public\s+class\s+(\w+)", code)
    return m.group(1) if m else None


def java_env() -> dict:
    """Environment with JDK 8 on PATH."""
    env = os.environ.copy()
    java_home = CONFIG["java_home"]
    env["JAVA_HOME"] = java_home
    env["PATH"] = f"{java_home}/bin:{env.get('PATH','')}"
    return env


def project_classpath(project: str) -> str:
    """Build classpath: project classes + project test-classes + junit + hamcrest."""
    root = Path(CONFIG["project_roots"][project])
    parts = [
        str(root / "target" / "classes"),
        str(root / "target" / "test-classes"),
        CONFIG["junit_jar"],
        CONFIG["hamcrest_jar"],
    ]
    # include any extra jars that some D4J projects ship in target/
    for extra in (root / "target").glob("*.jar"):
        parts.append(str(extra))
    return ":".join(p for p in parts if p)


def ensure_project_built(project: str) -> bool:
    """Make sure target/classes exists; build with maven if missing."""
    root = Path(CONFIG["project_roots"][project])
    classes = root / "target" / "classes"
    if classes.exists() and any(classes.rglob("*.class")):
        return True
    logger.info(f"  [{project}] target/classes missing - running mvn compile")
    try:
        subprocess.run(
            ["mvn", "-q", "-DskipTests=true", "compile"],
            cwd=str(root), env=java_env(),
            check=True, timeout=600,
            stdout=subprocess.PIPE, stderr=subprocess.PIPE,
        )
        return classes.exists()
    except Exception as e:
        logger.error(f"  [{project}] mvn compile failed: {e}")
        return False


# ============================================================================
# PHASE A - COMPILATION
# ============================================================================

def compile_test(code: str, class_name: str, project: str) -> tuple:
    """
    Compile a single test class to a temp directory.
    Returns (success: bool, diagnostics: str, class_dir: str).
    The caller owns class_dir cleanup (we keep it for run step).
    """
    package = CONFIG["test_packages"][project]
    pkg_path = package.replace(".", os.sep)

    work_dir = Path(tempfile.mkdtemp(prefix="task5_compile_"))
    src_dir = work_dir / "src" / pkg_path
    src_dir.mkdir(parents=True, exist_ok=True)
    out_dir = work_dir / "out"
    out_dir.mkdir(parents=True, exist_ok=True)

    src_file = src_dir / f"{class_name}.java"
    src_file.write_text(code, encoding="utf-8")

    cmd = [
        "javac",
        "-d", str(out_dir),
        "-cp", project_classpath(project),
        "-source", "1.8", "-target", "1.8",
        "-Xlint:none",
        str(src_file),
    ]
    try:
        result = subprocess.run(
            cmd, env=java_env(), capture_output=True, text=True, timeout=60
        )
    except subprocess.TimeoutExpired:
        shutil.rmtree(work_dir, ignore_errors=True)
        return False, "javac timed out", None

    if result.returncode == 0:
        return True, "", str(out_dir)

    diagnostics = (result.stderr or result.stdout or "").strip()
    # Strip the long temp path prefix to keep prompts compact
    diagnostics = diagnostics.replace(str(src_file), f"{class_name}.java")
    shutil.rmtree(work_dir, ignore_errors=True)
    return False, diagnostics, None


# ============================================================================
# PHASE B - EXECUTION + COVERAGE
# ============================================================================

def run_test_with_coverage(class_name: str, project: str, class_dir: str,
                           focal_class: str) -> tuple:
    """
    Execute the compiled test with JUnitCore under a JaCoCo agent.
    Returns (pass: bool, runtime_output: str, uncovered_lines: list[int]).
    """
    package = CONFIG["test_packages"][project]
    fqcn = f"{package}.{class_name}"

    work_dir = Path(class_dir).parent
    exec_file = work_dir / "jacoco.exec"
    report_xml = work_dir / "jacoco.xml"

    cp = ":".join([
        class_dir,
        project_classpath(project),
    ])

    # Limit JaCoCo instrumentation to the focal class (keeps the XML small).
    includes = focal_class
    agent_arg = f"-javaagent:{CONFIG['jacoco_agent_jar']}=destfile={exec_file},includes={includes}"

    cmd = [
        "java", agent_arg, "-cp", cp,
        "org.junit.runner.JUnitCore", fqcn,
    ]

    try:
        result = subprocess.run(
            cmd, env=java_env(),
            capture_output=True, text=True,
            timeout=CONFIG["test_run_timeout_sec"],
        )
    except subprocess.TimeoutExpired:
        return False, "Test run timed out", []

    runtime_output = (result.stdout + "\n" + result.stderr).strip()
    passed = result.returncode == 0

    # Generate XML coverage report
    uncovered = []
    if exec_file.exists():
        try:
            project_root = Path(CONFIG["project_roots"][project])
            classes_dir = project_root / "target" / "classes"
            src_dir = project_root / "src" / "main" / "java"
            subprocess.run(
                ["java", "-jar", CONFIG["jacoco_cli_jar"], "report",
                 str(exec_file),
                 "--classfiles", str(classes_dir),
                 "--sourcefiles", str(src_dir),
                 "--xml", str(report_xml)],
                env=java_env(), capture_output=True, text=True, timeout=60,
            )
            if report_xml.exists():
                uncovered = parse_uncovered_lines(report_xml, focal_class)
        except Exception as e:
            logger.warning(f"  jacoco report generation failed: {e}")

    return passed, runtime_output, uncovered


def parse_uncovered_lines(xml_path: Path, focal_class: str) -> list:
    """Return source line numbers that JaCoCo marked as missed for the focal class."""
    try:
        tree = ET.parse(str(xml_path))
        root = tree.getroot()
    except Exception:
        return []

    target_internal = focal_class.replace(".", "/")
    missed = []
    for cls in root.iter("class"):
        name = cls.get("name", "")
        # Match the focal class exactly OR any inner class of it
        if name == target_internal or name.startswith(target_internal + "$"):
            for line in cls.iter("line"):
                mi = int(line.get("mi", "0"))  # missed instructions
                mb = int(line.get("mb", "0"))  # missed branches
                if mi > 0 or mb > 0:
                    missed.append(int(line.get("nr", "0")))
    return sorted(set(missed))


# ============================================================================
# LLM CALLS
# ============================================================================

_client = None
def _openai_client():
    global _client
    if _client is None:
        _client = openai.OpenAI(api_key=CONFIG["api_key"])
    return _client


def call_llm(prompt: str) -> str:
    """Call the LLM with rate-limit retry. Returns raw text."""
    client = _openai_client()
    while True:
        try:
            resp = client.chat.completions.create(
                model=CONFIG["model"],
                messages=[{"role": "user", "content": prompt}],
                temperature=CONFIG["temperature"],
                max_tokens=CONFIG["max_tokens"],
            )
            return resp.choices[0].message.content
        except openai.RateLimitError:
            logger.warning("Rate limited - sleeping 30s")
            time.sleep(30)
        except Exception as e:
            logger.error(f"LLM call failed: {e}")
            return ""


def extract_java(raw: str) -> str:
    """Strip markdown fences and return raw Java code."""
    if not raw:
        return ""
    m = re.search(r"```java\s*\n?(.*?)```", raw, re.DOTALL)
    if m:
        return m.group(1).strip()
    m = re.search(r"```\s*\n?(.*?)```", raw, re.DOTALL)
    if m:
        return m.group(1).strip()
    return raw.strip()


def build_repair_prompt(code: str, diagnostics: str, row: pd.Series,
                        target_class_name: str) -> str:
    """Phase A repair prompt: code + compiler errors -> revised compilable test."""
    package = CONFIG["test_packages"][resolve_project(row[CONFIG["fqn_column"]])]
    return f"""You are repairing a JUnit 4 test class that fails to compile.

Focal method:
  FQN:        {row['FQN']}
  Signature:  {row.get('MethodSignature','')}
  Modifiers:  {row.get('Modifiers','')}
  Parameters: {row.get('Parameters','')}
  Throws:     {row.get('ThrownExceptions','')}

The current test class is below, followed by the javac diagnostics.
Fix every compilation error. You must:
  * Keep the package declaration: package {package};
  * Keep the public class name: {target_class_name}
  * Use JUnit 4 only (org.junit.Test, org.junit.Assert.*) - no JUnit 5.
  * Use JDK 8 syntax only.
  * Do NOT copy the focal method body into the test.
  * Output ONLY raw Java source code - no markdown fences, no commentary.

--- CURRENT TEST CODE ---
{code}

--- JAVAC DIAGNOSTICS ---
{diagnostics}
"""


def build_behavior_prompt(code: str, runtime_output: str, uncovered_lines: list,
                          row: pd.Series, target_class_name: str) -> str:
    """Phase B feedback prompt: failures + coverage gaps -> stronger test."""
    package = CONFIG["test_packages"][resolve_project(row[CONFIG["fqn_column"]])]
    src = row.get("SourceCode", "") or ""

    uncovered_block = ""
    if uncovered_lines:
        uncovered_block = (
            "Lines in the focal class that are NOT exercised by the current "
            "test (JaCoCo missed-instruction/branch line numbers):\n"
            f"  {uncovered_lines}\n"
            "Add targeted @Test methods that drive inputs reaching those "
            "branches/lines.\n"
        )

    runtime_block = ""
    if runtime_output.strip():
        # truncate long stack traces
        clipped = runtime_output[-2000:]
        runtime_block = (
            "Runtime output from the previous run (failures shown below) - "
            "fix any assertion mistakes and remove tests that rely on invalid "
            "assumptions about the focal method's behaviour:\n"
            f"{clipped}\n"
        )

    return f"""You are improving a JUnit 4 test class to better exercise the focal method.

Focal method:
  FQN:        {row['FQN']}
  Signature:  {row.get('MethodSignature','')}
  Modifiers:  {row.get('Modifiers','')}
  Parameters: {row.get('Parameters','')}
  Throws:     {row.get('ThrownExceptions','')}

Focal source code:
{src}

{runtime_block}
{uncovered_block}

Requirements for the revised test class:
  * Package: {package};
  * Public class name: {target_class_name}
  * JUnit 4 only (org.junit.Test, org.junit.Assert.*); JDK 8 syntax.
  * Add or modify @Test methods to (a) make all assertions pass against the
    actual focal method behaviour and (b) cover the missing branches/lines.
  * Do not copy the focal method body into the test.
  * Output ONLY raw Java source code - no markdown fences, no commentary.

--- CURRENT TEST CODE ---
{code}
"""


# ============================================================================
# DRIVER LOOPS
# ============================================================================

def run_phase_a(code: str, class_name: str, project: str, row: pd.Series) -> tuple:
    """
    Compilation repair loop. Returns (final_code, compiled: bool,
    final_class_dir: str|None, last_diagnostics: str, iterations: int).
    """
    diagnostics = ""
    class_dir = None
    iterations = 0

    for i in range(CONFIG["max_repair_iterations"] + 1):
        ok, diagnostics, class_dir = compile_test(code, class_name, project)
        if ok:
            return code, True, class_dir, "", i

        iterations = i + 1
        if i == CONFIG["max_repair_iterations"]:
            break  # no more LLM calls; bail out

        logger.info(f"    Phase A iter {i+1}: compile FAILED - requesting repair")
        prompt = build_repair_prompt(code, diagnostics, row, class_name)
        revised = extract_java(call_llm(prompt))
        time.sleep(CONFIG["api_delay"])
        if not revised:
            break
        code = revised

    return code, False, None, diagnostics, iterations


def run_phase_b(code: str, class_name: str, project: str, row: pd.Series,
                class_dir: str) -> tuple:
    """
    Behavior-guided improvement loop. The test must already compile.
    Returns (final_code, final_class_dir, compiled: bool, all_passed: bool).
    """
    focal_class = focal_class_fqn(row[CONFIG["fqn_column"]])
    passed, runtime_output, uncovered = run_test_with_coverage(
        class_name, project, class_dir, focal_class
    )
    logger.info(f"    Phase B initial run: passed={passed}, "
                f"uncovered_lines={len(uncovered)}")

    if passed and not uncovered:
        return code, class_dir, True, True

    for i in range(CONFIG["max_behavior_iterations"]):
        logger.info(f"    Phase B iter {i+1}: requesting behavior-guided regen")
        prompt = build_behavior_prompt(code, runtime_output, uncovered, row, class_name)
        revised = extract_java(call_llm(prompt))
        time.sleep(CONFIG["api_delay"])
        if not revised:
            break

        # Re-enter Phase A so any new compile errors get repaired too
        new_code, compiled, new_class_dir, _diag, _it = run_phase_a(
            revised, class_name, project, row
        )
        if not compiled:
            # behavior-guided revision broke compilation and could not be
            # repaired - keep the previous good version
            logger.info("    Phase B revision did not compile after repair; "
                        "keeping prior compilable version")
            return code, class_dir, True, passed

        code = new_code
        # discard the previous class_dir
        if class_dir and Path(class_dir).exists():
            shutil.rmtree(Path(class_dir).parent, ignore_errors=True)
        class_dir = new_class_dir

        passed, runtime_output, uncovered = run_test_with_coverage(
            class_name, project, class_dir, focal_class
        )
        logger.info(f"    Phase B iter {i+1} after regen: passed={passed}, "
                    f"uncovered_lines={len(uncovered)}")
        if passed and not uncovered:
            break

    return code, class_dir, True, passed


# ============================================================================
# FILE PERSISTENCE
# ============================================================================

def normalize_saved_path(saved_path: str, project: str) -> Path:
    """Saved paths in the CSV use Windows-style separators - normalize."""
    rel = saved_path.replace("\\", "/")
    return Path(CONFIG["project_roots"][project]) / rel


def write_back(code: str, saved_path: str, project: str) -> None:
    """Overwrite the .java file on disk with the final runnable code."""
    target = normalize_saved_path(saved_path, project)
    target.parent.mkdir(parents=True, exist_ok=True)
    target.write_text(code, encoding="utf-8")


# ============================================================================
# MAIN
# ============================================================================

def process_row(row: pd.Series) -> tuple:
    """
    Process one CSV row. Returns (runnable_code, compiled, passed,
    repair_iterations, behavior_iterations_used).
    """
    fqn = row[CONFIG["fqn_column"]]
    project = resolve_project(fqn)
    if project is None:
        return "", False, False, 0, 0

    code = row[CONFIG["code_column"]]
    if not isinstance(code, str) or not code.strip():
        return "", False, False, 0, 0

    class_name = detect_class_name(code)
    if not class_name:
        return code, False, False, 0, 0

    # Phase A
    final_code, compiled, class_dir, diag, iters_a = run_phase_a(
        code, class_name, project, row
    )

    if not compiled:
        logger.info(f"    Phase A: NOT compiled after {iters_a} iters")
        return final_code, False, False, iters_a, 0

    logger.info(f"    Phase A: compiled (iters={iters_a})")

    # Phase B
    final_code, class_dir, _compiled, passed = run_phase_b(
        final_code, class_name, project, row, class_dir
    )

    # cleanup compile workspace
    if class_dir and Path(class_dir).exists():
        shutil.rmtree(Path(class_dir).parent, ignore_errors=True)

    return final_code, True, passed, iters_a, CONFIG["max_behavior_iterations"]


def main():
    logger.info("=" * 70)
    logger.info("Task 5 Pipeline - Feedback Loop")
    logger.info("=" * 70)

    # Make sure each project's main classes are compiled (needed for javac
    # to resolve symbols when compiling our test files).
    for pkey in CONFIG["project_roots"]:
        if not ensure_project_built(pkey):
            logger.error(f"Cannot build project {pkey} - aborting.")
            sys.exit(1)

    df = pd.read_csv(CONFIG["input_csv"])
    logger.info(f"Loaded {len(df)} rows from {CONFIG['input_csv']}")

    runnable_codes = []
    stats = {"compiled": 0, "passed": 0, "total": len(df)}

    for idx, row in df.iterrows():
        fqn = row[CONFIG["fqn_column"]]
        logger.info("-" * 70)
        logger.info(f"[{idx+1}/{len(df)}] {fqn}")

        try:
            code, compiled, passed, iters_a, iters_b = process_row(row)
        except Exception as e:
            logger.exception(f"  Unexpected error on row {idx}: {e}")
            code, compiled, passed = "", False, False

        if compiled:
            stats["compiled"] += 1
            # Persist the final runnable test back into the project tree so
            # later tasks (e.g. coverage runs) operate on the repaired version.
            project = resolve_project(fqn)
            saved_path = row.get(CONFIG["saved_path_column"])
            if isinstance(saved_path, str) and saved_path and project:
                write_back(code, saved_path, project)
        if passed:
            stats["passed"] += 1

        runnable_codes.append(code)

    df[CONFIG["runnable_column"]] = runnable_codes
    df.to_csv(CONFIG["output_csv"], index=False)

    logger.info("=" * 70)
    logger.info("SUMMARY")
    logger.info("=" * 70)
    logger.info(f"Total tests:           {stats['total']}")
    logger.info(f"Compiled (runnable):   {stats['compiled']}")
    logger.info(f"Passed (no failures):  {stats['passed']}")
    logger.info(f"Output CSV:            {CONFIG['output_csv']}")


if __name__ == "__main__":
    main()
