# Task 1.1 — Crucial Code Information for High-Quality Unit Test Generation

This document is the deliverable for Task 1, part 1. For each piece of
code knowledge we use, we state:

- **Code Knowledge** — the column name in `task1_output.csv`.
- **Role** — how it supports test generation.
- **Target Methods** — the kinds of focal methods that rely on this
  particular piece of knowledge most heavily.

The extractor that collects these columns from the three Defects4J
target classes is [`Task1Extractor.java`](../Scripts/Task1Extractor.java);
its output is [`task1_output.csv`](task1_output.csv).

---

## Knowledge table

| # | Code Knowledge       | Role                                                                                                                                                                                                                                                                            | Target Methods                                                                                                                                                                |
|---|----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1 | **FQN**              | Anchors the focal method inside its package and class hierarchy so the test class can write the correct `import` and the correct qualified call (`pkg.Class.method(...)`).                                                                                                       | **All** focal methods — mandatory column.                                                                                                                                     |
| 2 | **MethodName**       | Short name used to form descriptive `@Test` method names (`test_<methodName>_<scenario>`) and to disambiguate when the enclosing class has overloads with the same signature.                                                                                                   | All methods, especially overloaded methods (e.g. `StringUtils.getBytes` has multiple charset variants).                                                                       |
| 3 | **MethodSignature**  | Gives the model the exact return type, parameter list, and `throws` clause. Without it the LLM either invents the wrong argument types or skips the `throws`.                                                                                                                   | Methods with non-trivial signatures: overloaded methods, generic methods (`MultiValueMap.getCollection`), and any method that throws checked exceptions (`TarUtils.parseOctal`). |
| 4 | **ReturnType**       | Determines which JUnit 4 assertion to choose (`assertTrue` for `boolean`, `assertArrayEquals` for `byte[]`, `assertEquals` for primitives/`String`, `assertNotNull` + size for `Collection`/`Map`, side-effect checks for `void`).                                                | All methods, but particularly **non-`void`** methods where assertion selection is non-obvious (`StringUtils.getBytes...` returns `byte[]`, `TarUtils.parseOctal` returns `long`). |
| 5 | **Modifiers**        | Tells the model whether to call the focal method **statically** (`ClassName.method(...)`) or to **instantiate** the enclosing class first (`new EnclosingClass(...).method(...)`). Also reveals access (`public/private/...`) so the LLM does not call an inaccessible overload. | All methods, but critical for **static utility classes** (`StringUtils`, `TarUtils`) vs. **instance classes** (`MultiValueMap`, `Values`, `ValuesIterator`).                  |
| 6 | **Parameters**       | Provides parameter names and concrete types, which the LLM uses to construct argument values that satisfy the static contract (e.g. building a `Charset`, a `byte[]`, or a populated `Map`).                                                                                    | Any method with **non-primitive parameters**, especially those taking `CharSequence`, `Charset`, `Map`, `Collection`, or `byte[]`.                                            |
| 7 | **ThrownExceptions** | Lists declared checked exceptions, which become the seed set for the `EXCEPTION_PATH` partition: tests are written that drive the focal method into each declared exception under a `try/catch` + `fail()` pattern.                                                              | Methods that declare `throws` — e.g. `StringUtils.getBytes(String,String) throws UnsupportedEncodingException`, `TarUtils.parseOctal(...)` throws `IllegalArgumentException`. |
| 8 | **MethodInvocations**| Lists every distinct method called inside the focal method body. This exposes internal dependencies the test setup must respect (e.g. a focal method that calls `Charset.forName(...)` needs the test to supply a valid charset name).                                          | Methods with substantial bodies and **internal helper calls**: `TarUtils.formatLongOctalOrBinaryBytes` (calls `formatLongBinary`), `MultiValueMap.putAll` (calls `put`).      |
| 9 | **Comments**         | The Javadoc on the focal method is a documented oracle. The model copies the documented examples (e.g. `StringUtils.equals(null,null)=true`) directly into assertions instead of guessing.                                                                                       | Methods with **non-trivial Javadoc** containing input/output examples or pre/post-conditions (`StringUtils.equals`, `TarUtils.parseOctal`, `MultiValueMap.put`).              |
| 10| **SourceCode**       | The full method body, including conditional branches and `throw` statements. Lets the LLM reason about which inputs reach each branch and which throws are explicit. This is the primary signal for the `BOUNDARY_VALUES` and `EXCEPTION_PATH` partitions.                       | Methods with **branches, loops, or explicit `throw` statements** — e.g. `TarUtils.parseOctal` (length checks), `MultiValueMap.removeMapping` (early-return branches).         |

## Why these ten, and only these ten

We started from the failure modes observed in Assignment 2:

1. tests that did not compile because the LLM invented call syntax,
2. tests that compiled but called methods statically when they were
   instance methods (or vice versa),
3. tests whose assertions guessed an expected value rather than
   reading the contract,
4. tests that ignored entire `throws` clauses.

Each of the ten columns above directly mitigates at least one of those
failure modes:

- **Call-syntax errors** are mitigated by **FQN**, **MethodSignature**,
  **Modifiers**, and **Parameters**.
- **Wrong assertions** are mitigated by **ReturnType** and **Comments**
  (which carry the Javadoc oracle).
- **Missed exception paths** are mitigated by **ThrownExceptions**
  combined with the `throw`-statement view in **SourceCode**.
- **Missed branches** are mitigated by **SourceCode** and
  **MethodInvocations**.

We deliberately stopped at ten columns because adding more (full AST
nodes, Jimple IR, callers from other classes, class fields) increases
prompt size with diminishing returns: GPT-4o-mini's 4 K-token budget is
already pressured by a long `SourceCode` for methods like
`TarUtils.formatLongOctalOrBinaryBytes`. Anything that did not directly
fix one of the four failure modes above was left out.

## How these columns feed the rest of the pipeline

- **Task 2** (`Task2Strategy.java`) reads `ThrownExceptions` and
  `SourceCode` to decide whether to emit the `EXCEPTION_PATH` row for a
  focal method (only if the method actually throws).
- **Task 3** (`Task3PromptFiller.java`) substitutes every column above
  into the prompt template's `#{...}#` placeholders.
- **Task 5** uses **SourceCode**, **Comments**, **MethodInvocations**,
  and **ThrownExceptions** again when building the behaviour-guided
  repair prompt, so the same knowledge base is reused at feedback time.
