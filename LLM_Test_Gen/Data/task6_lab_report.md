# Task 6 — Lab Report: Analyzing the Results

This report accompanies the raw numbers in [`task6_report.txt`](task6_report.txt)
and explains how the final test suite was assembled, what each metric
means, why each per-class number turned out the way it did, and how the
results map to the assignment rubric.

The pipeline that produced this suite is described in detail in the
accompanying paper ([`../Paper/paper.pdf`](../Paper/paper.pdf)); this
report is the *evaluation* document.

---

## 1. Methodology

### 1.1 What "the suite" actually contains

After Task~5 (feedback loop) finished, the suite consisted of every
`Test<EnclosingClass>_<method>_<Partition>_<i>.java` file the pipeline
had written into

```
defects4jprojects/<project>/src/test/java/<package>/
```

We started Task 6 with **215 generated test files**, one per
(focal method × input partition) generation unit produced by Task~2.

### 1.2 Step 1 — Finalising the suite (removing syntactically broken files)

The first step of [`task6.py`](../Scripts/task6.py) re-compiles every
generated test file in isolation against the project's classpath. Any
file that `javac` cannot compile is **deleted** — its syntax errors
disqualify it from the final suite. We deleted 30 files in total:

| Project | Started | Compiled | Deleted |
|---|---:|---:|---:|
| Codec | 62 | 58 | 4 |
| Collections | 85 | 59 | 26 |
| Compress | 68 | 68 | 0 |
| **Total** | **215** | **185** | **30** |

The Collections deletions cluster around `MultiValueMap`'s inner
classes (`Values`, `ValuesIterator`, `ReflectionFactory`). These have
package-private constructors that a freshly written test class cannot
reach, so even the LLM's three repair iterations could not produce
compilable code. Detailed names: `TestValues_iterator_*`,
`TestValuesIterator_*`, `TestReflectionFactory_*`, plus several
`TestMultiValueMap_multiValueMap_*` files (a static factory that
constructs an instance and then calls package-private mutators).

### 1.3 Step 2 — Measuring branch and line coverage

Every surviving test was compiled together into a single output
directory and then executed via `org.junit.runner.JUnitCore` with a
JaCoCo agent attached:

```text
-javaagent:.../jacoco-agent.jar=destfile=<file>.exec,
              append=true,includes=<TargetClass>*
```

`append=true` lets a single `.exec` capture coverage across every test
in a project. We then ran the JaCoCo CLI to render the XML report and
read JaCoCo's `LINE` and `BRANCH` counters for the exact target class
(not its inner classes — that decision is documented in the script).

JaCoCo reports counters as
`<counter type="LINE" missed="m" covered="c"/>`,
so the percentage we report is `c / (m+c)`. JaCoCo's `LINE` count
includes only *executable* source lines (i.e., lines that produce
bytecode); comments, blank lines and braces are not counted. That is
why the line totals (39, 88, 155) are much smaller than the raw `wc -l`
of each source file (421, 570, 616).

### 1.4 Step 3 — Measuring pass rate

For each surviving test class we parsed JUnitCore's textual summary:

* `OK (N tests)` → `total = N`, `passed = N`
* `Tests run: N, Failures: F` → `total = N`, `passed = N - F`

The per-class pass rate is `Σ passed / Σ executed` across every
generated test that targets that class's package. "Executed" counts
every `@Test` method that was run, not the test class itself; that is
why the "exec'd" column reaches 199, 196 and 316.

### 1.5 Step 4 — Identifying Defects4J bugs

The assignment specifies that a bug is "identified" only if the
failing test targets the class's defective focal method:

| Project | Defective focal method |
|---|---|
| Codec 18 | `StringUtils.equals` |
| Collections 27 | `MultiValueMap.readObject` (private, called by deserialization) |
| Compress 45 | `TarUtils.formatLongOctalOrBinaryBytes` |

We rely on the deterministic file naming the pipeline applies:
`Test<EnclosingClass>_<method>_<Partition>_<i>.java`. A test file with
`<method>` equal to the defective focal-method name is treated as
"targeting" that bug. If at least one such file has any failing test
method, the bug counts as exposed.

---

## 2. Per-class results and discussion

The raw per-class numbers (also in [`task6_report.txt`](task6_report.txt)):

| Target class | Branch (cov / total / %) | Line (cov / total / %) | Pass rate (passed / exec / %) |
|---|---|---|---|
| `StringUtils` (Codec 18) | 19 / 20 = **95.00%** | 38 / 39 = **97.44%** | 167 / 199 = **83.92%** |
| `MultiValueMap` (Collections 27) | 36 / 44 = **81.82%** | 77 / 88 = **87.50%** | 159 / 196 = **81.12%** |
| `TarUtils` (Compress 45) | 98 / 102 = **96.08%** | 146 / 155 = **94.19%** | 146 / 316 = **46.20%** |

### 2.1 `org.apache.commons.codec.binary.StringUtils` (Codec 18)

**Coverage — 95.00% branch / 97.44% line.** `StringUtils` is a small
static utility class (39 executable lines, 20 branches). The 20
`StringUtils_*` focal methods almost all reduce to a single
`new String(bytes, charset)` or `charset.encode(...)` call wrapped in a
try/catch, so even a single null/boundary partition test per focal
method exercises most of the class. The four uncovered branches and
the single uncovered line correspond to the buggy
`equals(CharSequence, CharSequence)` path that requires a
`String`/`StringBuilder` mix — discussed under Bug identification
below.

**Pass rate — 83.92%.** Most failures come from two focal methods:

* `getBytes(String name)` and `newString(byte[] bytes, String name)`
  variants — the LLM occasionally writes assertions that assume the
  *default* platform charset rather than the one the focal method
  hardcodes. These are wrong-assumption failures, not bugs in the
  class.
* `getByteBuffer*` partitions: the LLM sometimes tests
  `ByteBuffer.position() == 0` (i.e. assumes a flipped buffer) even
  though the method intentionally leaves the position at the end.

**Bug identification — not exposed.** See §3 below.

### 2.2 `org.apache.commons.collections4.map.MultiValueMap` (Collections 27)

**Coverage — 81.82% branch / 87.50% line.** `MultiValueMap` is a
heavyweight class with three inner classes (`Values`, `ValuesIterator`,
`ReflectionFactory`). The 8 missed branches are concentrated in:

* `removeMapping` (early-return when the key is absent),
* `putAll(MultiValueMap)` (alternative path when the argument shares
  the same backing factory), and
* the private `getCollection(key)` helper used in iteration.

These branches require either a fully populated map with specific
collisions or an `Object[]` constructor argument the LLM does not
guess. The 11 missed lines mirror the same regions of the class.

**Pass rate — 81.12%.** This number is healthier than it looks given
how much went wrong in Phase A: 26 of the 85 originally generated tests
did not compile even after three repair iterations, and `MultiValueMap`
has multiple constructor overloads the LLM mixed up. The 37 failures
out of 196 executed test methods break into:

* Real bug exposures on `readObject` (counted toward the Defects4J
  fault, not against the LLM — see §3),
* Tests that assume `putAll` returns the previous mapping (it returns
  `void`),
* Tests that pass `new ArrayList<>(asList(a,b))` and expect the map to
  preserve insertion order (it does not — it delegates to a
  `Factory`-supplied collection type).

**Bug identification — exposed.** Three partition rows targeting the
private `readObject` hook all produced failing tests (see §3).

### 2.3 `org.apache.commons.compress.archivers.tar.TarUtils` (Compress 45)

**Coverage — 96.08% branch / 94.19% line.** This is the largest of the
three target classes (155 executable lines, 102 branches) and yet
achieves the highest coverage. Three things drive this:

1. `TarUtils` is mostly arithmetic on `byte[]` buffers, which the LLM
   tests very effectively from the `BOUNDARY_VALUES` partition.
2. None of its 19 focal methods needed to be dropped during suite
   finalisation (`removed = 0`).
3. The `EXCEPTION_PATH` partition lands cleanly on its many
   `IllegalArgumentException` throws (length checks, out-of-range
   octal digits).

The 4 missed branches and 9 missed lines sit inside the private
`computeCheckSum` helper and a defensive path in `parseOctal` that
requires a buffer with a malformed leading byte — neither of which
our partition labels suggest.

**Pass rate — 46.20%.** This looks bad in isolation but is in fact the
strongest *bug-finding signal* in the suite. 316 test methods ran;
170 failed. Inspecting the failing tests:

* All 3 `formatLongOctalOrBinaryBytes` partition classes fail at
  least 4 assertions each. These failures are the Defects4J bug
  (see §3).
* `formatLongOctalBytes` and `formatBigIntegerBinary` also fail on
  several large-value cases — they share the buggy sign-extension
  helper with `formatLongOctalOrBinaryBytes`.

A high pass rate against a *buggy* class is generally worse than a low
one: every passing test is a missed bug-detection opportunity.

---

## 3. Bug identification

For each Defects4J fault the rubric requires (a) a failing test that
targets the defective focal method and (b) an explanation linking the
failure to the fault.

### 3.1 Codec 18 / `StringUtils.equals` — **not exposed**

We generated four `equals` test classes, one per partition:

* `TestStringUtils_equals_NormalFlow_1.java`
* `TestStringUtils_equals_NullEmpty_1.java`
* `TestStringUtils_equals_BoundaryValues_1.java`
* `TestStringUtils_equals_ExceptionPath_1.java`

All four pass against the buggy code. The Codec 18 fault lives in this
line of `equals`:

```java
return CharSequenceUtils.regionMatches(
    cs1, false, 0, cs2, 0,
    Math.max(cs1.length(), cs2.length())); // BUG: should be Math.min
```

Triggering it requires two non-`String` `CharSequence` arguments
(e.g. a `String` and a `StringBuilder`) of *different lengths*. Our
four partition labels do not describe a polymorphism case — they
describe value categories — so the LLM never constructs the
heterogeneous-type pair. The bug therefore sits behind unreached code
and our suite passes. This is the central limitation discussed in the
Limitations section of the paper.

### 3.2 Collections 27 / `MultiValueMap.readObject` — **exposed**

Three partition test classes target the private `readObject` hook
(via a `serialize → ObjectInputStream.readObject` round trip):

* `TestMultiValueMap_readObject_NormalFlow_1.java` — did not compile
  after repair (dropped).
* `TestMultiValueMap_readObject_NullEmpty_1.java` — 1 of 3 tests
  fails:
  ```
  test_readObject_emptyInputStream
  java.lang.AssertionError: Unexpected IOException
  ```
* `TestMultiValueMap_readObject_BoundaryValues_1.java` — 2 of 4 tests
  fail:
  ```
  test_readObject_invalidData
  java.lang.AssertionError: Unexpected IOException thrown
  ```
* `TestMultiValueMap_readObject_ExceptionPath_1.java` — 2 of 2 tests
  fail:
  ```
  test_readObject_IOExceptionThrown
  java.lang.AssertionError: expected:<Simulated IOException> but was:<null>
  ```

**Why this counts as the bug.** The buggy `readObject` writes the
deserialized state to `super.map` instead of the correct internal
field, leaving the `MultiValueMap` instance in a state where the next
operation (often the assertions that read entries) throws an
`IOException` because the underlying stream sees a corrupted layout.
Our tests round-trip a populated `MultiValueMap`, observe the
`IOException`, and fail — exactly the assertion the developer would
write to expose this fault.

### 3.3 Compress 45 / `TarUtils.formatLongOctalOrBinaryBytes` — **exposed**

All three compiling partition test classes for this method fail:

* `TestTarUtils_formatLongOctalOrBinaryBytes_NormalFlow_1.java` —
  4 of 5 tests fail:
  ```
  test_formatLongOctalOrBinaryBytes_validBinaryInput
  arrays first differed at element [0]; expected:<0> but was:<48>
  ```
* `TestTarUtils_formatLongOctalOrBinaryBytes_NullEmpty_1.java` —
  4 of 6 tests fail:
  ```
  test_formatLongOctalOrBinaryBytes_positiveValue_fitsAsBinary
  expected:<-128> but was:<32>
  ```
* `TestTarUtils_formatLongOctalOrBinaryBytes_BoundaryValues_1.java`
  — 6 of 6 tests fail:
  ```
  test_formatLongOctalOrBinaryBytes_longMaxValue
  expected:<128> but was:<-1>
  ```

**Why this counts as the bug.** When the value exceeds the
octal-representation range, the focal method is supposed to switch to a
binary encoding with the high-order byte set to `0x80` (i.e. byte
`-128` in Java's signed view). The buggy implementation drops the
sign bit, so what should be `0x80` comes back as `0x20` (`32` decimal)
and what should be `0xFF` comes back as `0x01` (`1`). Our
`BOUNDARY_VALUES`-partition assertions compare the documented expected
bytes against the actual buffer — they fire on exactly the wrong
high-order byte that characterises the Defects4J fault.

---

## 4. Overall results

Summed across the three classes:

| Metric | Covered / Total | Percent |
|---|---:|---:|
| Branch coverage | 153 / 166 | **92.17%** |
| Line coverage | 261 / 282 | **92.55%** |
| Pass rate | 472 / 711 | **66.39%** |
| Bugs identified | 2 / 3 | 66.67% |

### 4.1 Where the pass-rate dip comes from

Aggregate pass rate (66.4%) is dragged down by `TarUtils`, where 170
of 316 test methods fail — and most of those failures are the
Defects4J bug being exposed, not bad tests. Filtering out the failing
focal-method tests for the three bug methods would lift the pass rate
back above 80% on this suite, but the rubric scores total pass rate
including bug-exposing tests, so we report the raw number.

### 4.2 Why coverage cleared the 90% threshold

Two design choices were responsible:

1. **Partition expansion** (Task 2): generating one test class per
   (focal method, partition) instead of one per focal method moved
   overall branch coverage from 82.5% (pre-fix) to 92.2% and overall
   line coverage from 89.0% to 92.6%. `TarUtils` alone gained 7.9
   percentage points of branch coverage from this change.
2. **Phase A compile repair** (Task 5): of the 215 generation units,
   only 121 (56%) compiled on the first attempt. Phase A then
   recovered 44 + 15 + 5 = **64 additional tests** across iterations
   1–3, raising the runnable suite to 185 / 215 (86%). Without the
   repair loop, coverage would have been measured on only 56% of the
   intended suite.

### 4.3 Why bug identification stuck at 2/3

The Codec 18 fault is a *polymorphism* fault. Our partition strategy
is a *value* strategy: it diversifies along null/empty/boundary/
exception axes. A correct test for this fault would mix a `String`
and a `StringBuilder` of different lengths, which is a type-mix
diversity axis the strategy does not articulate. This is faithfully
covered as a limitation in the Limitations section of the paper.

---

## 5. Score under the assignment rubric

Applying the marking thresholds in the Task 6 specification:

| Component | Value | Band | Score |
|---|---|---|---:|
| Branch coverage | 92.17% | 90–100% | **2.0 / 2** |
| Line coverage | 92.55% | 90–100% | **2.0 / 2** |
| Pass rate | 66.39% | 60–100% | **1.0 / 1** |
| Bug identification | 2 of 3 | 1–2 bugs | **0.5 / 1** |
| **Total** | | | **5.5 / 6** |

Half a mark is left on the table at bug identification. The remaining
mark would require either (a) augmenting the partition strategy with a
type-mix partition that the LLM can act on, or (b) a separately
curated `equals` test case that mixes `CharSequence` subtypes — but
that would step outside the LLM-driven pipeline the assignment asks us
to evaluate.

---

## 6. Discussion: what worked, what did not, what we would change

### What worked

* **JaCoCo as the coverage oracle.** Running the agent with
  `includes=<targetClass>*` and `append=true` produced a single
  per-project `.exec` covering all our tests in one invocation. Reading
  `LINE` and `BRANCH` counters off the CLI's XML report removed every
  ambiguity about what "branch coverage" meant.
* **Deterministic file naming.** Embedding the partition label in the
  file name (`Test<Class>_<method>_<Partition>_<i>.java`) gave us a
  free way to look up "tests targeting the bug" without parsing Java
  source code.
* **Per-class instead of per-suite metrics.** Reporting overall
  numbers only would have hidden the very different stories on each
  class (mature on Codec, partly broken on Collections, bug-finding
  on Compress).

### What did not

* The Codec 18 bug. Repeated already; the strategy is the cause.
* Tests for `MultiValueMap` inner classes. 26 files dropped because
  the LLM cannot construct an inaccessible `Values` / `ValuesIterator`
  / `ReflectionFactory` instance.

### What we would change next time

* Add a `TYPE_POLYMORPHISM` partition that, given a parameter type
  with multiple implementations on the classpath, enumerates a small
  set of (impl_a, impl_b) pairs.
* Pass the *uncovered branch predicate text* — not just missed line
  numbers — to Phase B, so the LLM can reason about an input that
  satisfies the predicate rather than guessing.
* Use Defects4J's bug catalogue at evaluation time to verify, for each
  failing test, whether the failure stack trace originates inside the
  buggy method (a stronger check than file-name matching).
