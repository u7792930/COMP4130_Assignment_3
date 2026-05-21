"""
Task 6: Analyzing the Results
==============================
Pipeline:
  1. Finalize the test suite for each of the three Defects4J projects by
     re-compiling every generated test file. Any file that still fails to
     compile after Task 5 is deleted (its syntax errors disqualify it from
     the final suite).
  2. Compile all surviving generated tests against the project's classes.
  3. Run each surviving test class under JUnitCore with a JaCoCo agent
     attached, accumulating coverage into one .exec per project. Count
     test methods executed / passed / failed and remember which class each
     test came from.
  4. Use the JaCoCo CLI to render an XML coverage report restricted to the
     three target classes (StringUtils, MultiValueMap, TarUtils) and parse
     LINE / BRANCH counters out of it.
  5. Identify Defects4J bug exposure: a bug counts as identified only if
     a test targeting the defective focal method failed:
       - codec      : StringUtils.equals
       - collections: MultiValueMap.readObject (via Object*Stream)
       - compress   : TarUtils.formatLongOctalOrBinaryBytes
  6. Print a per-class report table, overall coverage, overall pass rate,
     and the computed Task-6 score using the assignment rubric.

Usage:
    python task6.py
"""

import os
import re
import shutil
import subprocess
import xml.etree.ElementTree as ET
from collections import defaultdict
from pathlib import Path

# Reuse Task 5 configuration so paths stay in lock-step.
from task5 import CONFIG, java_env, project_classpath, ensure_project_built

REPO_ROOT = Path(__file__).resolve().parents[2]

TARGET_CLASSES = {
    "codec":       "org.apache.commons.codec.binary.StringUtils",
    "collections": "org.apache.commons.collections4.map.MultiValueMap",
    "compress":    "org.apache.commons.compress.archivers.tar.TarUtils",
}

# A failing test for one of these focal methods means the Defects4J bug
# has been exposed. The values are the second segment of the file name,
# e.g. "TestStringUtils_equals_1.java" -> "equals".
BUG_FOCAL_METHODS = {
    "codec":       "equals",
    "collections": "readObject",
    "compress":    "formatLongOctalOrBinaryBytes",
}

OUT_DIR = REPO_ROOT / "LLM_Test_Gen" / "Data"
REPORT_TXT = OUT_DIR / "task6_report.txt"


# ============================================================================
# STEP 1 - FINALIZE: drop files that no longer compile
# ============================================================================

def collect_generated_tests(project_key: str) -> list:
    """Return absolute paths to all generated Test*.java files in the package dir."""
    root = Path(CONFIG["project_roots"][project_key])
    test_src = root / CONFIG["test_src_dirs"][project_key]
    pkg = CONFIG["test_packages"][project_key].replace(".", os.sep)
    pkg_dir = test_src / pkg
    files = []
    if pkg_dir.exists():
        for f in sorted(pkg_dir.iterdir()):
            if f.is_file() and f.name.startswith("Test") and f.suffix == ".java":
                files.append(f)
    return files


def compile_single(file_path: Path, project_key: str, out_dir: Path) -> tuple:
    """Compile one .java file. Returns (success, stderr)."""
    cmd = [
        "javac",
        "-d", str(out_dir),
        "-cp", project_classpath(project_key),
        "-source", "1.8", "-target", "1.8", "-Xlint:none",
        str(file_path),
    ]
    res = subprocess.run(cmd, env=java_env(), capture_output=True, text=True,
                         timeout=60)
    return res.returncode == 0, (res.stderr or res.stdout or "").strip()


def finalize_suite(project_key: str) -> tuple:
    """
    Compile every test in the project's test package one-by-one.
    Delete files that still fail to compile.
    Return (kept_paths, removed_paths).
    """
    work = Path("/tmp") / f"task6_finalize_{project_key}"
    if work.exists():
        shutil.rmtree(work)
    work.mkdir(parents=True)

    kept, removed = [], []
    for f in collect_generated_tests(project_key):
        ok, _ = compile_single(f, project_key, work)
        if ok:
            kept.append(f)
        else:
            removed.append(f)
            f.unlink()

    shutil.rmtree(work, ignore_errors=True)
    return kept, removed


# ============================================================================
# STEP 2 - COMPILE ALL SURVIVING TESTS TOGETHER
# ============================================================================

def compile_all(project_key: str, files: list) -> Path:
    """Compile all kept tests into one classes dir. Returns the dir."""
    classes_dir = Path("/tmp") / f"task6_classes_{project_key}"
    if classes_dir.exists():
        shutil.rmtree(classes_dir)
    classes_dir.mkdir(parents=True)

    if not files:
        return classes_dir

    cmd = [
        "javac",
        "-d", str(classes_dir),
        "-cp", project_classpath(project_key),
        "-source", "1.8", "-target", "1.8", "-Xlint:none",
    ] + [str(f) for f in files]
    res = subprocess.run(cmd, env=java_env(), capture_output=True, text=True,
                         timeout=180)
    if res.returncode != 0:
        # Should be rare since we already filtered, but if a test compiles
        # in isolation but conflicts with siblings, drop the offenders.
        print(f"  [{project_key}] batch compile failed; falling back to "
              f"one-by-one")
        shutil.rmtree(classes_dir)
        classes_dir.mkdir(parents=True)
        for f in files:
            single_cmd = [
                "javac", "-d", str(classes_dir),
                "-cp", project_classpath(project_key),
                "-source", "1.8", "-target", "1.8", "-Xlint:none", str(f),
            ]
            r = subprocess.run(single_cmd, env=java_env(),
                               capture_output=True, text=True, timeout=60)
            if r.returncode != 0:
                # remove the offending source so we stay consistent
                print(f"    drop {f.name}: {r.stderr.splitlines()[0] if r.stderr else r.stdout.splitlines()[0]}")
                f.unlink(missing_ok=True)
    return classes_dir


# ============================================================================
# STEP 3 - RUN ALL TESTS WITH JaCoCo ATTACHED
# ============================================================================

JUNIT_SUMMARY_RE = re.compile(r"OK \((\d+) tests?\)")
JUNIT_FAILURE_RE = re.compile(r"Tests run: (\d+),\s*Failures: (\d+)")


def parse_junit_output(stdout: str) -> tuple:
    """Return (total, passed). Counts both Failures and Errors as not passed."""
    m_ok = JUNIT_SUMMARY_RE.search(stdout)
    if m_ok:
        n = int(m_ok.group(1))
        return n, n
    m_fail = JUNIT_FAILURE_RE.search(stdout)
    if m_fail:
        total = int(m_fail.group(1))
        failed = int(m_fail.group(2))
        return total, total - failed
    return 0, 0


def class_name_from_file(java_file: Path) -> str:
    return java_file.stem


def run_tests(project_key: str, files: list, test_classes_dir: Path,
              exec_file: Path) -> list:
    """
    Execute each surviving test under JUnitCore with the JaCoCo agent
    appending coverage into exec_file. Returns a list of per-test records:
        {file, class_name, total, passed, failed, stdout}
    """
    package = CONFIG["test_packages"][project_key]
    cp = ":".join([str(test_classes_dir), project_classpath(project_key)])
    target_internal_pkg = TARGET_CLASSES[project_key]

    # Wide enough to record any focal class the tests touch; will be
    # filtered to target class at report time.
    includes = f"{target_internal_pkg}*"

    records = []
    for f in files:
        cls = class_name_from_file(f)
        fqcn = f"{package}.{cls}"
        agent = (f"-javaagent:{CONFIG['jacoco_agent_jar']}="
                 f"destfile={exec_file},append=true,includes={includes}")
        cmd = ["java", agent, "-cp", cp,
               "org.junit.runner.JUnitCore", fqcn]
        try:
            r = subprocess.run(cmd, env=java_env(), capture_output=True,
                               text=True, timeout=CONFIG["test_run_timeout_sec"])
            out = (r.stdout + "\n" + r.stderr).strip()
        except subprocess.TimeoutExpired:
            out = "TIMEOUT"
            r = None

        total, passed = parse_junit_output(out)
        records.append({
            "file": f,
            "class_name": cls,
            "total": total,
            "passed": passed,
            "failed": total - passed,
            "stdout": out,
        })
    return records


# ============================================================================
# STEP 4 - GENERATE / PARSE JaCoCo XML REPORT
# ============================================================================

def make_coverage_report(project_key: str, exec_file: Path,
                         report_xml: Path) -> bool:
    root = Path(CONFIG["project_roots"][project_key])
    classes_dir = root / "target" / "classes"
    src_dir = root / "src" / "main" / "java"
    cmd = [
        "java", "-jar", CONFIG["jacoco_cli_jar"], "report",
        str(exec_file),
        "--classfiles", str(classes_dir),
        "--sourcefiles", str(src_dir),
        "--xml", str(report_xml),
    ]
    r = subprocess.run(cmd, env=java_env(), capture_output=True, text=True,
                       timeout=120)
    return r.returncode == 0 and report_xml.exists()


def parse_class_counters(report_xml: Path, target_class: str) -> dict:
    """Return {LINE: (missed, covered), BRANCH: (missed, covered)} for the class."""
    out = {"LINE": [0, 0], "BRANCH": [0, 0]}
    target_internal = target_class.replace(".", "/")
    if not report_xml.exists():
        return out
    try:
        tree = ET.parse(str(report_xml))
    except ET.ParseError:
        return out

    for cls in tree.getroot().iter("class"):
        name = cls.get("name", "")
        # Count the focal class only (NOT its inner classes) so coverage
        # reflects the class file we were asked to measure.
        if name != target_internal:
            continue
        for c in cls.findall("counter"):
            ctype = c.get("type")
            if ctype in out:
                out[ctype][0] += int(c.get("missed", "0"))
                out[ctype][1] += int(c.get("covered", "0"))
    return out


# ============================================================================
# STEP 5 - BUG IDENTIFICATION
# ============================================================================

def file_targets_focal(java_file: Path, focal_method: str) -> bool:
    """File names follow Test<Class>_<method>_<index>.java"""
    name = java_file.stem
    parts = name.split("_")
    # Test<Class> is parts[0], focal method is parts[1]
    return len(parts) >= 3 and parts[1] == focal_method


def identify_bug(records: list, focal_method: str) -> dict:
    """Find any failing test that targets the focal method."""
    failing = []
    for r in records:
        if not file_targets_focal(r["file"], focal_method):
            continue
        if r["failed"] > 0 or r["total"] == 0:
            failing.append(r)
    return {
        "exposed": len(failing) > 0,
        "failing_tests": failing,
    }


# ============================================================================
# STEP 6 - REPORTING
# ============================================================================

def pct(num: int, denom: int) -> float:
    return 100.0 * num / denom if denom else 0.0


def score_coverage(percent: float) -> float:
    if percent < 10: return 0.0
    if percent < 50: return 0.5
    if percent < 80: return 1.0
    if percent < 90: return 1.5
    return 2.0


def score_pass_rate(percent: float) -> float:
    if percent < 30: return 0.0
    if percent < 60: return 0.5
    return 1.0


def score_bugs(n_exposed: int) -> float:
    if n_exposed == 0: return 0.0
    if n_exposed < 3: return 0.5
    return 1.0


def fmt_pct(num: int, denom: int) -> str:
    return f"{num}/{denom} = {pct(num,denom):.2f}%"


# ============================================================================
# MAIN
# ============================================================================

def main():
    lines_out = []
    def emit(s=""):
        print(s)
        lines_out.append(s)

    emit("=" * 78)
    emit("Task 6 - Analyzing the Results")
    emit("=" * 78)

    # Make sure each project's main sources are compiled.
    for k in CONFIG["project_roots"]:
        if not ensure_project_built(k):
            emit(f"[FATAL] cannot build {k}")
            return

    summary = {}
    total_lines_missed = total_lines_covered = 0
    total_branches_missed = total_branches_covered = 0
    total_passed = total_executed = 0
    bugs_exposed = 0

    for project_key in ("codec", "collections", "compress"):
        emit("")
        emit("-" * 78)
        emit(f"Project: {project_key}")
        emit(f"Target class: {TARGET_CLASSES[project_key]}")
        emit(f"Bug focal method: {BUG_FOCAL_METHODS[project_key]}")
        emit("-" * 78)

        # 1. finalize
        kept, removed = finalize_suite(project_key)
        emit(f"Generated test files: {len(kept) + len(removed)}")
        emit(f"  kept (compiled):    {len(kept)}")
        emit(f"  removed (syntax):   {len(removed)}")
        for r in removed:
            emit(f"    - {r.name}")

        if not kept:
            emit("No runnable tests for this project; skipping coverage.")
            continue

        # 2. compile all kept tests
        test_classes_dir = compile_all(project_key, kept)

        # rebuild kept (compile_all may have deleted conflicting files)
        kept = [f for f in kept if f.exists()]

        # 3. run all tests with jacoco
        exec_file = Path("/tmp") / f"task6_{project_key}.exec"
        report_xml = Path("/tmp") / f"task6_{project_key}.xml"
        if exec_file.exists():
            exec_file.unlink()
        if report_xml.exists():
            report_xml.unlink()

        records = run_tests(project_key, kept, test_classes_dir, exec_file)

        # 4. generate coverage report
        ok = make_coverage_report(project_key, exec_file, report_xml)
        counters = (parse_class_counters(report_xml, TARGET_CLASSES[project_key])
                    if ok else {"LINE": [0, 0], "BRANCH": [0, 0]})
        lm, lc = counters["LINE"]
        bm, bc = counters["BRANCH"]
        line_total = lm + lc
        branch_total = bm + bc

        # 5. pass rate
        executed = sum(r["total"] for r in records)
        passed = sum(r["passed"] for r in records)

        # 6. bug identification
        bug = identify_bug(records, BUG_FOCAL_METHODS[project_key])

        emit("")
        emit(f"  Branch coverage: {fmt_pct(bc, branch_total)}  "
             f"(covered={bc}, total={branch_total})")
        emit(f"  Line coverage:   {fmt_pct(lc, line_total)}  "
             f"(covered={lc}, total={line_total})")
        emit(f"  Tests executed:  {executed}")
        emit(f"  Tests passed:    {passed}")
        emit(f"  Pass rate:       {fmt_pct(passed, executed)}")
        emit(f"  Bug exposed?     {bug['exposed']}")
        if bug["exposed"]:
            for r in bug["failing_tests"]:
                first_line = r["stdout"].splitlines()
                stack = "\n      ".join(first_line[:20])
                emit(f"    Failing focal-method test: {r['file'].name}")
                emit(f"      JUnit summary: total={r['total']}, "
                     f"passed={r['passed']}, failed={r['failed']}")
                # show a couple of failure lines
                tail = [l for l in first_line if "test_" in l or "Failure" in l
                        or "expected" in l or "AssertionError" in l]
                for t in tail[:6]:
                    emit(f"      {t}")

        # aggregate
        total_lines_missed += lm
        total_lines_covered += lc
        total_branches_missed += bm
        total_branches_covered += bc
        total_executed += executed
        total_passed += passed
        if bug["exposed"]:
            bugs_exposed += 1

        summary[project_key] = {
            "kept": len(kept), "removed": len(removed),
            "line_total": line_total, "line_covered": lc,
            "branch_total": branch_total, "branch_covered": bc,
            "executed": executed, "passed": passed,
            "bug_exposed": bug["exposed"],
            "bug_records": bug["failing_tests"],
        }

    # ============== overall ==============
    emit("")
    emit("=" * 78)
    emit("OVERALL (summed across the three target classes)")
    emit("=" * 78)

    total_lines = total_lines_missed + total_lines_covered
    total_branches = total_branches_missed + total_branches_covered

    overall_branch_pct = pct(total_branches_covered, total_branches)
    overall_line_pct = pct(total_lines_covered, total_lines)
    overall_pass_pct = pct(total_passed, total_executed)

    emit(f"Branch coverage: {fmt_pct(total_branches_covered, total_branches)}")
    emit(f"Line coverage:   {fmt_pct(total_lines_covered, total_lines)}")
    emit(f"Pass rate:       {fmt_pct(total_passed, total_executed)}")
    emit(f"Bugs exposed:    {bugs_exposed}/3")

    s_branch = score_coverage(overall_branch_pct)
    s_line   = score_coverage(overall_line_pct)
    s_pass   = score_pass_rate(overall_pass_pct)
    s_bugs   = score_bugs(bugs_exposed)
    total_score = s_branch + s_line + s_pass + s_bugs

    emit("")
    emit("Score (Task 6 rubric)")
    emit(f"  Branch coverage  : {s_branch} / 2")
    emit(f"  Line coverage    : {s_line} / 2")
    emit(f"  Pass rate        : {s_pass} / 1")
    emit(f"  Bug identification: {s_bugs} / 1")
    emit(f"  TOTAL            : {total_score} / 6")

    # ============== per-class compact table ==============
    emit("")
    emit("=" * 78)
    emit("Per-class table")
    emit("=" * 78)
    header = f"{'Class':<55} {'BranchCov':>14} {'LineCov':>14} {'PassRate':>14}"
    emit(header)
    emit("-" * len(header))
    for k in ("codec", "collections", "compress"):
        s = summary.get(k)
        if not s:
            emit(f"{TARGET_CLASSES[k]:<55} {'-':>14} {'-':>14} {'-':>14}")
            continue
        bcv = f"{s['branch_covered']}/{s['branch_total']} ({pct(s['branch_covered'],s['branch_total']):.1f}%)"
        lcv = f"{s['line_covered']}/{s['line_total']} ({pct(s['line_covered'],s['line_total']):.1f}%)"
        psr = f"{s['passed']}/{s['executed']} ({pct(s['passed'],s['executed']):.1f}%)"
        emit(f"{TARGET_CLASSES[k]:<55} {bcv:>14} {lcv:>14} {psr:>14}")

    OUT_DIR.mkdir(parents=True, exist_ok=True)
    REPORT_TXT.write_text("\n".join(lines_out) + "\n", encoding="utf-8")
    emit("")
    emit(f"Report saved to: {REPORT_TXT}")


if __name__ == "__main__":
    main()
