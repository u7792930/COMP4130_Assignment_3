"""
Task 4 Pipeline: Unit Test Generation, Formatting & Saving
===========================================================
This script handles both Task 4.1 (LLM generation) and Task 4.2 (formatting/saving).

Prerequisites:
    pip install openai pandas

Usage:
    1. Update the CONFIG section below with your paths and API key.
    2. Run:  python task4_pipeline.py

Inputs:
    - Task 1 CSV with extracted code information (one row per focal method/generation unit)
    - Task 3 prompt template file with placeholders like #{FQN}#, #{SourceCode}#, etc.

Outputs:
    - Updated CSV with columns: "Generated Code", "Code After Formatting", "Saved Path"
    - .java test files saved into each Defects4J project's test source tree
"""

import openai
import pandas as pd
import re
import os
import time
import hashlib
import logging
from pathlib import Path

# ============================================================================
# CONFIG — Update these before running
# ============================================================================

CONFIG = {
    # Your OpenAI API key
    "api_key": "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",

    # Path to your Task 1 CSV
    "input_csv": "LLM_Test_Gen/data/task1_output.csv",

    # Path to your Task 3 prompt template
    "prompt_template_file": "LLM_Test_Gen/Scripts/prompt_template.txt",

    # Output CSV path
    "output_csv": "LLM_Test_Gen/data/task4_output.csv",

    # Column name in your CSV that holds the fully qualified name
    "fqn_column": "FQN",

    # Defects4J project root directories (where you checked out each buggy version)
    # Adjust these paths to match your local setup
    "project_roots": {
        "codec":       "defects4jprojects/Codec_18_buggy",
        "collections": "defects4jprojects/Collections_27_buggy",
        "compress":    "defects4jprojects/Compress_45_buggy",
    },

    # Test source directories relative to project root
    # Defects4J projects vary — adjust if needed
    "test_src_dirs": {
        "codec":       "src/test/java",
        "collections": "src/test/java",
        "compress":    "src/test/java",
    },

    # Package paths for generated tests (where .java files will be saved)
    "test_packages": {
        "codec":       "org.apache.commons.codec.binary",
        "collections": "org.apache.commons.collections4.map",
        "compress":    "org.apache.commons.compress.archivers.tar",
    },

    # Mapping: FQN substring -> project key
    # Used to route each focal method to the correct project
    "fqn_to_project": {
        "commons.codec":       "codec",
        "commons.collections4": "collections",
        "commons.compress":    "compress",
    },

    # GPT-4o-mini settings
    "model": "gpt-4o-mini",
    "temperature": 0.7,
    "max_tokens": 2048,

    # Rate limiting: seconds to wait between API calls
    "api_delay": 1.0,

    # Placeholder format in your prompt template
    # Supports both #{COLUMN_NAME}# and {COLUMN_NAME} styles
    # Set to the one you use. The script tries both.
    "placeholder_prefix": "#{",
    "placeholder_suffix": "}#",
}

# ============================================================================
# LOGGING
# ============================================================================

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(message)s",
    handlers=[
        logging.FileHandler("task4_pipeline.log"),
        logging.StreamHandler()
    ]
)
logger = logging.getLogger(__name__)

# ============================================================================
# TASK 4.1 — GENERATE UNIT TESTS VIA GPT-4o-mini
# ============================================================================

def load_prompt_template(path: str) -> str:
    """Load the prompt template from file."""
    with open(path, "r", encoding="utf-8") as f:
        return f.read()


def fill_prompt(template: str, row: pd.Series) -> str:
    """
    Replace all placeholders in the template with values from the CSV row.
    Handles both #{COLUMN}# and {COLUMN} placeholder styles.
    """
    prompt = template
    for col in row.index:
        value = str(row[col]) if pd.notna(row[col]) else ""
        # Try #{COLUMN}# style
        prompt = prompt.replace(f"#{{  {col}}}#", value)
        prompt = prompt.replace(f"#{{{col}}}#", value)
        # Try {COLUMN} style
        prompt = prompt.replace(f"{{{col}}}", value)
        # Try ###COLUMN style (from spec example)
        prompt = prompt.replace(f"###{col}", value)
    return prompt


def call_gpt4o_mini(client: openai.OpenAI, prompt: str, config: dict) -> str:
    """Call GPT-4o-mini and return the generated text."""
    try:
        response = client.chat.completions.create(
            model=config["model"],
            messages=[{"role": "user", "content": prompt}],
            temperature=config["temperature"],
            max_tokens=config["max_tokens"],
        )
        return response.choices[0].message.content
    except openai.RateLimitError:
        logger.warning("Rate limited. Waiting 30 seconds...")
        time.sleep(30)
        return call_gpt4o_mini(client, prompt, config)
    except Exception as e:
        logger.error(f"API call failed: {e}")
        return f"ERROR: {e}"


def generate_tests(df: pd.DataFrame, template: str, config: dict) -> pd.DataFrame:
    """Task 4.1: Generate test code for every row in the CSV."""
    client = openai.OpenAI(api_key=config["api_key"])
    generated_codes = []

    total = len(df)
    for idx, row in df.iterrows():
        fqn = row[config["fqn_column"]]
        logger.info(f"[{idx + 1}/{total}] Generating test for: {fqn}")

        prompt = fill_prompt(template, row)
        code = call_gpt4o_mini(client, prompt, config)
        generated_codes.append(code)

        logger.info(f"  -> Generated {len(code)} chars")
        time.sleep(config["api_delay"])

    df["Generated Code"] = generated_codes
    return df


# ============================================================================
# TASK 4.2 — FORMAT AND SAVE
# ============================================================================

def extract_java_code(raw: str) -> str:
    """
    Extract Java code from raw LLM output.
    Handles markdown fences, natural language preamble/postamble.
    """
    if not raw or raw.startswith("ERROR:"):
        return raw

    # Try ```java ... ``` first
    match = re.search(r"```java\s*\n?(.*?)```", raw, re.DOTALL)
    if match:
        return match.group(1).strip()

    # Try generic ``` ... ```
    match = re.search(r"```\s*\n?(.*?)```", raw, re.DOTALL)
    if match:
        return match.group(1).strip()

    # Fallback: find from first line that looks like Java (import/package/public/import)
    lines = raw.split("\n")
    start = None
    end = None
    for i, line in enumerate(lines):
        stripped = line.strip()
        if start is None and (
            stripped.startswith("package ")
            or stripped.startswith("import ")
            or stripped.startswith("public class")
            or stripped.startswith("@")
            or stripped.startswith("//")
        ):
            start = i
        if start is not None:
            end = i

    if start is not None and end is not None:
        return "\n".join(lines[start:end + 1]).strip()

    # Last resort: return as-is
    return raw.strip()


def detect_class_name(code: str) -> str:
    """Extract the existing class name from the code."""
    match = re.search(r"public\s+class\s+(\w+)", code)
    if match:
        return match.group(1)
    return None


def generate_unique_class_name(fqn: str, index: int) -> str:
    """
    Generate a unique test class name from the FQN and an index.
    e.g. "org.apache.commons.codec.binary.StringUtils.equals" -> "TestStringUtils_equals_1"
    """
    parts = fqn.split(".")
    if len(parts) >= 2:
        class_name = parts[-2]  # enclosing class
        method_name = parts[-1]  # method
    elif len(parts) == 1:
        class_name = parts[0]
        method_name = "method"
    else:
        class_name = "Unknown"
        method_name = "method"

    # Clean names: remove anything non-alphanumeric
    class_name = re.sub(r"[^a-zA-Z0-9]", "", class_name)
    method_name = re.sub(r"[^a-zA-Z0-9]", "", method_name)

    # Handle cases where method_name matches common patterns like <init> or <clinit>
    method_name = method_name.replace("init", "Init").replace("clinit", "Clinit")

    return f"Test{class_name}_{method_name}_{index}"


def set_package_declaration(code: str, package: str) -> str:
    """Ensure the code has the correct package declaration."""
    # Remove existing package declaration if present
    code = re.sub(r"^\s*package\s+[\w.]+\s*;\s*\n?", "", code, count=1)
    # Add correct package at the top
    return f"package {package};\n\n{code}"


def rename_class_in_code(code: str, old_name: str, new_name: str) -> str:
    """Rename the test class throughout the code (declaration + constructor refs)."""
    if old_name and old_name != new_name:
        # Replace class declaration
        code = re.sub(
            rf"public\s+class\s+{re.escape(old_name)}",
            f"public class {new_name}",
            code,
            count=1
        )
        # Replace constructor calls (if the test has a constructor)
        code = code.replace(f"new {old_name}(", f"new {new_name}(")
    elif not old_name:
        # No class found — this is a problem, but try to wrap it
        logger.warning(f"No class declaration found. Code may not be valid Java.")
    return code


def ensure_junit_import(code: str) -> str:
    """Make sure basic JUnit 4 imports are present."""
    required_imports = [
        "import org.junit.Test;",
        "import static org.junit.Assert.*;",
    ]
    for imp in required_imports:
        if imp.replace(" ", "") not in code.replace(" ", ""):
            # Add after last import or after package
            if "import " in code:
                last_import = max(code.rfind("\nimport "), code.rfind(";import "))
                # Find end of that import line
                end_of_line = code.find("\n", last_import + 1)
                if end_of_line == -1:
                    end_of_line = len(code)
                code = code[:end_of_line + 1] + imp + "\n" + code[end_of_line + 1:]
            elif "package " in code:
                pkg_end = code.find(";") + 1
                code = code[:pkg_end] + "\n\n" + imp + "\n" + code[pkg_end:]
    return code


def resolve_project(fqn: str, config: dict) -> str:
    """Determine which project a focal method belongs to based on its FQN."""
    for substring, project_key in config["fqn_to_project"].items():
        if substring in fqn:
            return project_key
    logger.error(f"Cannot resolve project for FQN: {fqn}")
    return None


def clear_test_directory(project_key: str, config: dict):
    """Clear generated test files from the target directory before saving new ones."""
    project_root = config["project_roots"][project_key]
    test_src = config["test_src_dirs"][project_key]
    package = config["test_packages"][project_key]
    pkg_path = package.replace(".", os.sep)
    full_dir = os.path.join(project_root, test_src, pkg_path)

    if os.path.exists(full_dir):
        # Only remove files matching our naming pattern (Test*_*_*.java)
        for f in os.listdir(full_dir):
            if f.startswith("Test") and f.endswith(".java"):
                os.remove(os.path.join(full_dir, f))
                logger.info(f"  Cleared: {f}")


def save_test_file(code: str, class_name: str, project_key: str, config: dict) -> str:
    """Save a formatted test file to the correct project directory."""
    project_root = config["project_roots"][project_key]
    test_src = config["test_src_dirs"][project_key]
    package = config["test_packages"][project_key]
    pkg_path = package.replace(".", os.sep)

    full_dir = os.path.join(project_root, test_src, pkg_path)
    os.makedirs(full_dir, exist_ok=True)

    file_path = os.path.join(full_dir, f"{class_name}.java")
    with open(file_path, "w", encoding="utf-8") as f:
        f.write(code)

    # Return relative path from project root
    rel_path = os.path.relpath(file_path, project_root)
    return rel_path


def format_and_save_tests(df: pd.DataFrame, config: dict) -> pd.DataFrame:
    """Task 4.2: Format raw generated code and save as .java files."""
    formatted_codes = []
    saved_paths = []

    # Track unique class names globally to avoid collisions
    used_names = set()
    # Counter per FQN for generating unique indices
    fqn_counter = {}

    # Clear target directories first
    cleared_projects = set()
    for _, row in df.iterrows():
        project = resolve_project(row[config["fqn_column"]], config)
        if project and project not in cleared_projects:
            logger.info(f"Clearing test directory for: {project}")
            clear_test_directory(project, config)
            cleared_projects.add(project)

    total = len(df)
    for idx, row in df.iterrows():
        fqn = row[config["fqn_column"]]
        raw_code = row["Generated Code"]
        logger.info(f"[{idx + 1}/{total}] Formatting: {fqn}")

        # Step 1: Extract Java code from raw output
        code = extract_java_code(raw_code)

        if not code or code.startswith("ERROR:"):
            formatted_codes.append(code or "")
            saved_paths.append("")
            logger.warning(f"  -> Skipped (no valid code)")
            continue

        # Step 2: Determine project and package
        project = resolve_project(fqn, config)
        if not project:
            formatted_codes.append(code)
            saved_paths.append("")
            continue

        package = config["test_packages"][project]

        # Step 3: Generate unique class name
        if fqn not in fqn_counter:
            fqn_counter[fqn] = 0
        fqn_counter[fqn] += 1
        unique_name = generate_unique_class_name(fqn, fqn_counter[fqn])

        # Handle collisions
        while unique_name in used_names:
            fqn_counter[fqn] += 1
            unique_name = generate_unique_class_name(fqn, fqn_counter[fqn])
        used_names.add(unique_name)

        # Step 4: Rename class in code
        old_name = detect_class_name(code)
        code = rename_class_in_code(code, old_name, unique_name)

        # Step 5: Fix package declaration
        code = set_package_declaration(code, package)

        # Step 6: Ensure JUnit imports
        code = ensure_junit_import(code)

        # Step 7: Save to project tree
        rel_path = save_test_file(code, unique_name, project, config)

        formatted_codes.append(code)
        saved_paths.append(rel_path)
        logger.info(f"  -> Saved as: {rel_path}")

    df["Code After Formatting"] = formatted_codes
    df["Saved Path"] = saved_paths
    return df


# ============================================================================
# MAIN
# ============================================================================

def main():
    logger.info("=" * 60)
    logger.info("Task 4 Pipeline — Starting")
    logger.info("=" * 60)

    # Load inputs
    logger.info(f"Loading CSV: {CONFIG['input_csv']}")
    df = pd.read_csv(CONFIG["input_csv"])
    logger.info(f"  -> {len(df)} rows loaded")

    logger.info(f"Loading prompt template: {CONFIG['prompt_template_file']}")
    template = load_prompt_template(CONFIG["prompt_template_file"])
    logger.info(f"  -> {len(template)} chars loaded")

    # Task 4.1: Generate
    logger.info("-" * 60)
    logger.info("TASK 4.1: Generating tests via GPT-4o-mini")
    logger.info("-" * 60)
    df = generate_tests(df, template, CONFIG)

    # Save intermediate results
    intermediate_path = CONFIG["output_csv"].replace(".csv", "_after_generation.csv")
    df.to_csv(intermediate_path, index=False)
    logger.info(f"Intermediate CSV saved: {intermediate_path}")

    # Task 4.2: Format and Save
    logger.info("-" * 60)
    logger.info("TASK 4.2: Formatting and saving test files")
    logger.info("-" * 60)
    df = format_and_save_tests(df, CONFIG)

    # Save final CSV
    df.to_csv(CONFIG["output_csv"], index=False)
    logger.info(f"Final CSV saved: {CONFIG['output_csv']}")

    # Print summary
    logger.info("=" * 60)
    logger.info("SUMMARY")
    logger.info("=" * 60)
    total = len(df)
    saved = df["Saved Path"].apply(lambda x: bool(x) if pd.notna(x) else False).sum()
    logger.info(f"Total focal methods/units: {total}")
    logger.info(f"Tests saved successfully:  {saved}")
    logger.info(f"Failed/skipped:            {total - saved}")

    # Per-project breakdown
    for project_key in CONFIG["project_roots"]:
        project_count = 0
        for _, row in df.iterrows():
            p = resolve_project(row[CONFIG["fqn_column"]], CONFIG)
            if p == project_key and pd.notna(row.get("Saved Path")) and row.get("Saved Path"):
                project_count += 1
        logger.info(f"  {project_key}: {project_count} tests saved")


if __name__ == "__main__":
    main()