# Fuzzing-Inspired LLM-Based Unit Test Generation

An iterative, feedback-driven framework that leverages large language models (GPT-4o-mini) to automatically generate high-quality Java unit tests. Inspired by fuzz testing principles, the system extracts rich code context, steers the model toward diverse test inputs, and uses compilation and coverage feedback to repair and improve tests across multiple rounds.

## Target Projects

Tests are generated for focal methods in the following [Defects4J](https://github.com/rjust/defects4j) buggy classes:

| Project | Buggy Version | Target Class |
|---|---|---|
| Commons Codec | 18 | `org/apache/commons/codec/binary/StringUtils.java` |
| Commons Collections | 27 | `org/apache/commons/collections4/map/MultiValueMap.java` |
| Commons Compress | 45 | `org/apache/commons/compress/archivers/tar/TarUtils.java` |

## Pipeline Overview

```
┌────────────────┐     ┌────────────────┐     ┌────────────────┐
│  Code Context   │────▶│  LLM Generation │────▶│  Format & Save  │
│  Extraction     │     │  (GPT-4o-mini)  │     │  (.java files)  │
└────────────────┘     └────────────────┘     └────────────────┘
        │                      ▲                       │
        │                      │                       ▼
        │               ┌──────┴───────┐      ┌────────────────┐
        │               │  Feedback     │◀─────│  Compile & Run  │
        │               │  Loop         │      │  (JaCoCo)       │
        │               └──────────────┘      └────────────────┘
        │                      │
        ▼                      ▼
   CSV Artifacts         Final Test Suite
                         + Coverage Report
```

1. **Extraction** — Static analysis (SootUp + JavaParser) extracts FQNs, signatures, Jimple IR, source code, comments, dependencies, and other context into a structured CSV.
2. **Strategy** — A fuzzing-inspired strategy partitions generation targets to maximise path diversity and avoid duplicate inputs.
3. **Prompting** — A structured, zero-shot prompt template encodes the extracted context and strategy-specific fields.
4. **Generation & Formatting** — GPT-4o-mini produces raw test code, which is then cleaned, renamed for uniqueness, and saved into each project's test tree.
5. **Feedback Loop** — Phase A repairs compilation failures (up to 3 iterations). Phase B uses coverage gaps and runtime failures to retarget generation.
6. **Analysis** — Branch coverage, line coverage, pass rate, and bug identification are measured and reported per class.

## Repository Structure

```
├── extractor/            # Code context extraction tooling (Task 1)
├── strategy/             # Fuzzing strategy implementation (Task 2)
├── prompts/              # Prompt templates (Task 3)
├── generation/           # LLM generation & formatting pipeline (Task 4)
├── feedback/             # Compilation repair & behaviour-guided loop (Task 5)
├── analysis/             # Coverage measurement & result tables (Task 6)
├── paper/                # LaTeX source for the research paper (Task 7)
├── data/                 # CSVs and intermediate artifacts
├── results/              # Final coverage reports and tables
└── README.md
```

## Task Split

### Member A — Extraction & Infrastructure

**Owns:** Task 1 (1.1 + 1.2), Task 4.2

| Responsibility | Details |
|---|---|
| Code knowledge identification | Determine which context types (FQN, signature, Jimple IR, source, comments, modifiers, dependencies, etc.) are needed for high-quality test generation across all three target classes. |
| Automated extractor | Build the SootUp + JavaParser pipeline that traverses each project and writes per-method rows to a CSV with one column per knowledge type. |
| Formatting & saving pipeline | Clean raw LLM output into compilable Java, assign unique class names, and save files into the correct Defects4J test source tree. |
| Build & coverage tooling | Set up Defects4J checkouts, compilation scripts, and JaCoCo/Defects4J coverage measurement used by the whole team. |
| Report contribution | Task 1 writeup, "Approach" subsection on extraction, relevant Evaluation content. |

**Timeline:** Starts day 1 (no dependencies). Delivers CSV schema immediately; full CSVs by day 2–3.

---

### Member B — Strategy, Prompting & Generation

**Owns:** Task 2, Task 3, Task 4.1

| Responsibility | Details |
|---|---|
| Fuzzing-inspired strategy | Design the diversity mechanism (e.g., CFG-path partitioning, boundary-value targeting, exception-path enumeration) and define how feedback steers later rounds. |
| Prompt template | Write the structured zero-shot prompt with `@persona`, `@terminology`, `@instruction`, and placeholders for all extracted fields and strategy metadata. |
| LLM generation | Run GPT-4o-mini over all focal methods using the filled prompts; store raw output in the "Generated Code" CSV column. |
| Report contribution | Task 2 and 3 writeup, "Approach" subsections on strategy and prompting, Task 4.1 discussion. |

**Timeline:** Agrees on CSV schema with Member A on day 1; develops strategy and prompt against sample data in parallel; switches to real data once A delivers CSVs.

---

### Member C — Feedback Loop, Analysis & Paper Lead

**Owns:** Task 5, Task 6, Task 7 (lead author/editor)

| Responsibility | Details |
|---|---|
| Compilation repair loop (Phase A) | Feed failing tests + compiler diagnostics back to GPT-4o-mini for up to 3 repair iterations per test. |
| Behaviour-guided loop (Phase B) | Use coverage gaps and runtime failures to retarget or mutate prompts for under-exercised paths. |
| Result analysis | Compute per-class branch coverage, line coverage, pass rate; identify the three Defects4J bugs with failing-test evidence. |
| Research paper | Draft and edit the full LaTeX paper (Abstract, Introduction, Motivation, Approach figure, Evaluation tables, Limitations, Conclusions). A and B contribute their sections; C integrates and polishes. |
| Report contribution | Task 5 and 6 writeup, paper structure and final editing, Limitations, Conclusions. |

## Tech Stack

- **Java 8** — target language and JUnit 4 for tests
- **SootUp** — Jimple IR extraction, control-flow analysis
- **JavaParser** — AST, source code, comments, modifiers
- **GPT-4o-mini** — test generation and repair (via OpenAI API)
- **Defects4J** — buggy project checkouts and test execution
- **JaCoCo** — code coverage measurement
- **LaTeX** — research paper (IEEE conference template)
- **Python** — pipeline orchestration and CSV processing
