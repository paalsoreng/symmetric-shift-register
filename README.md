# sym-shift-register

**sym-shift-register** is a Kotlin implementation of algorithms for computing periods of symmetric shift registers, based on the mathematical work of Jan Søreng.

A symmetric shift register is a binary feedback shift register with a special symmetry property. Determining the period of such a register – that is, how many steps it takes before the state repeats – is a non-trivial mathematical problem. This project provides efficient algorithms that solve this problem analytically, without having to simulate the register step by step.

The algorithms are derived from the theory described in the arXiv article:
> [arXiv:2505.23974 – Symmetric Shift Registers and Their Periods (Jan Søreng, 2024)](https://arxiv.org/abs/2505.23974)

The project also includes a simulation module for verification, a high-level API for easy integration, and tooling for reporting and analysis.

_Current version: **1.0.0**_

## Contents

- **part1 – part8**: Core algorithms for analysis and calculation of periods in symmetric shift registers.
- **api**: High-level API (`SymmetricShiftRegisterApi`) for easy use of the algorithms, including conversion between vector and binary representations, and period calculation.
- **simulation**: Step-by-step simulation of shift registers by iterating the feedback rule directly (`Simulation`).
- **startvector**: Tools for finding valid start vectors and associated parameters (`StartVector`, `AllCases`).
- **report**: Report generation for analyzed vectors (`ShiftAndFlipReportGenerator`).
- **testdata**: Test data utilities, including random vector generation (`RandomVector`).
- **trace**: Logging and tracing of intermediate calculations (`ExecutionLog`, `ReportBuilder`, `ReportPrinter`).
- **ExhaustivePeriodVerificationRunner**: Main runnable program that generates random vectors, computes periods both algorithmically and by simulation, verifies that results are identical, and writes a report to file.
- **ShiftRegisterWalkthrough**: Runnable program that walks through a concrete example step by step, illustrating how the algorithm computes the period.
- **PeriodCalculationReport**: Runnable program that shows the full analytical calculation behind the period for a given vector, with intermediate results.

## Documentation

For more information about this project, see:

- **arXiv Article:**
  The original mathematical article describing the theory and algorithms for symmetric shift registers.
  [arXiv:2505.23974 – Symmetric Shift Registers and Their Periods (Jan Søreng, 2024)](https://arxiv.org/abs/2505.23974)

- **Start- and User Guide:**
  The Kotlin program `PeriodCalculationReport` shows the full analytical calculation behind the period, including all intermediate results. A web-based version of this program is also available at [paalsoreng.github.io](https://paalsoreng.github.io/). To understand the input and output, we recommend reading the [Start Guide (PDF)](./A002_StartGuide_190426.pdf) and the [User Guide (PDF)](./B006_UserGuide_170426.pdf).


## What is a Symmetric Shift Register?

A **shift register** is a sequence of binary cells (bits). At each step, all bits shift one position to the left, and a new bit is computed and inserted at the right end. The new bit is determined by a **feedback function** that looks at the current state.

In a **symmetric shift register** as defined by Jan Søreng, the feedback rule depends on two parameters, `k` and `p`, and works as follows:

- Let `ω` be the sum of all bits **except** the first (i.e., bits 2 through n).
- Let `a₁` be the leftmost (outgoing) bit.
- The new bit inserted at the right is:
  - The **complement** of `a₁` (i.e., flip 0↔1) if `k ≤ ω ≤ k + p`
  - Otherwise, `a₁` unchanged.

The **period** of the register is the number of steps before the register returns exactly to its initial state.

### Relationship between k, p and V

The parameters `k` and `p` are not independent of each other or of V. Given a vector V, let `w(A)` denote the number of `1`-bits in the binary representation of V. Then `k` and `p` must satisfy:

```
w(A) = k + p + 1
```

This means that once V and p are chosen (with p valid, i.e. `p + 1 ≤ hMax`), the value of k is fully determined:

```
k = w(A) − (p + 1)
```

In practice this is exactly what `findSuitableK(V, p)` computes. The valid range of p (from 0 to `hMax − 1`) therefore also determines the valid range of k (from `w(A) − 1` down to `w(A) − hMax`).

### Example

Consider a 5-bit register with parameters `k = 2`, `p = 0`:

```
Step  Register state    ω   Feedback rule           New bit
  0   [1, 1, 0, 1, 0]  2   2 ≤ 2 ≤ 2  → flip a₁=1    0
  1   [1, 0, 1, 0, 0]  1   1 ∉ [2,2]  → keep a₁=1    1
  2   [0, 1, 0, 0, 1]  2   2 ≤ 2 ≤ 2  → flip a₁=0    1
  3   [1, 0, 0, 1, 1]  2   2 ≤ 2 ≤ 2  → flip a₁=1    0
  4   [0, 0, 1, 1, 0]  2   2 ≤ 2 ≤ 2  → flip a₁=0    1
  5   [0, 1, 1, 0, 1]  3   3 ∉ [2,2]  → keep a₁=0    0
  6   [1, 1, 0, 1, 0]  ← back to start!
```

The period is **6**. Without the theory in this project, finding such periods for large registers would require simulating every step. The algorithms here compute the period analytically in a fraction of the time.

---

## Algorithm Walkthrough

This section traces the full algorithm behind `GeneralCase.findMinimalPeriod(V, p)` – the main entry point for analytically computing the period of a symmetric shift register.

The input is:
- **V** – the vector representation of the register (a list of positive integers encoding run lengths)
- **p** – a non-negative integer parameter (must satisfy `p + 1 ≤ hMax`, see step 1)

The output is the **minimal period** – the number of steps before the register returns to its initial state.

### Overview

```
findMinimalPeriod(V, p)
│
├── 1. Validate p against hMax      (MaximalityProperty / AlternatingSums)
│
├── 2. Contract V exactly p times   (ContractionVector × p)
│        └── each contraction uses: Tau, DecompositionParameters, InternalIndexFunction
│
├── 3. Extend the final contracted vector   (ExtensionVector)
│
├── 4. Find base cyclic parameters (j, B)   (CyclicParameters)
│        └── uses: EvenFactors, ShiftMap
│
└── 5. Walk back up through each contraction level, updating (j, B)
         └── AlgorithmicFunction for each level n = p-1 downTo 0
                  ├── Simple case: no interior 1s → B += j
                  └── General case:
                           ├── DistanceVector  (CIndexes, Tau, IndexFunction)
                           ├── ParameterAlpha  (Tau)
                           ├── ProgressionParameters (GreatestCommonDivisor, AuxiliaryFunction)
                           └── LeastSolution   (GreatestCommonDivisor)
                                    → new (j, B)

Final result: B  (the minimal period)
```

### Step 1 – Validate p using the Alternating Sums

**Code:** `MaximalityProperty.calculate(V)` → `AlternatingSums.calculate(V)`

Before anything else, the algorithm checks that the parameter `p` is valid for the given vector `V`.

`AlternatingSums` builds a sequence `h` where each element is a running alternating sum of V:

```
h[0] = 0
h[i] = h[i-1] + V[i-1] * (-1)^(i-1)
     = h[i-1] + V[i-1],  - V[i-1],  + V[i-1], ...
```

`MaximalityProperty` then scans `h` to find `hMax` – the highest value reached before `h` first drops to zero or below.

**Requirement:** `p + 1 ≤ hMax`. If this fails, the algorithm throws an error – `p` is too large for this vector.

### Step 2 – Contract V exactly p times

**Code:** `ContractionVector.calculate(V)`, called `p` times

This is the structural heart of the algorithm. It reduces V into a sequence of contracted vectors `V[0], V[1], ..., V[p]`, where each is a "compressed" version of the previous one.

Each contraction involves three sub-steps:

#### 2a – Compute Tau

`Tau.calculate(V)` builds a cumulative index sequence:

```
tau[0] = 0
tau[i] = tau[i-1] + V[i-1] - 1
```

This maps positions in V into an index space used by later steps.

#### 2b – Find Decomposition Parameters (r)

`DecompositionParameters.calculate(V)` finds a list of "anchor indices" `r = [r0, r1, r2, ...]` that mark the boundaries of the symmetric structure in V.

It works by repeatedly calling `InternalIndexFunction`:

```
r[0] = 0
r[j+1] = r[j] + 2 * tMax + 1

where tMax = number of consecutive 1s in V starting just after r[j]
```

`InternalIndexFunction` counts how many `1`s appear in V at positions `r+1, r+3, r+5, ...` before hitting a non-1.

#### 2c – Build the contracted vector π

Given `tau` and `r`, the contracted vector `π` is:

```
π[i] = tau[r[i+1]] - tau[r[i]]    for i = 0 .. len(r)-2
π[last] += 1
```

This `π` becomes the input V for the next contraction level.

The result is a list: `V = [V0, V1, ..., Vp]` where `V0` is the original and each subsequent entry is the contraction of the previous.

### Step 3 – Extend the final contracted vector

**Code:** `ExtensionVector.calculate(V[p])`

The last contracted vector `V[p]` is "extended" by incrementing its final element by 1:

```
Vp_ext = [..., V[p].last() + 1]
```

This extended vector is used as input to the cyclic parameter calculation.

### Step 4 – Find base cyclic parameters (j, B)

**Code:** `CyclicParameters.calculate(Vp_ext)`

This finds the smallest cyclic shift `j` such that shifting `Vp_ext` left by `j` positions gives back the same vector, and computes `B` as the sum of the first `j` elements.

```
for each even divisor e of len(Vp_ext):
    if ShiftMap(Vp_ext, e) == Vp_ext:
        j = e
        B = sum(Vp_ext[0 .. e-1])
        return (j, B)
```

`ShiftMap` does a cyclic left rotation by `e` positions.
`EvenFactors` returns the even divisors of the vector's length in ascending order.

At this point `(j, B)` describe the periodicity at the deepest contraction level.

### Step 5 – Walk back up: AlgorithmicFunction for each level

**Code:** `AlgorithmicFunction.calculate(V[n], j, B)` for `n = p-1 downTo 0`

This is the main "unwinding" loop. Starting from the base cyclic parameters `(j, B)` computed at level `p`, the algorithm works back up through each contraction level, updating `(j, B)` at each step. The final `B` at level 0 is the minimal period.

At each level, there are two cases:

#### Simple case – no interior 1s

If V has no `1`s in positions `1 .. s-2` (i.e., only the first and/or last elements can be 1), the update is trivial:

```
new_B = B + j
new_j = j        (unchanged)
```

#### General case

Otherwise, the algorithm computes a new `(j, B)` through four sub-steps:

##### 5a – Distance Vector D

`DistanceVector.calculate(V)` finds a list of "distance" values that capture the internal structure of V at this level.

First, `CIndexes.calculate(V)` finds a chain of indices `c = [c0, c1, ..., cγ]` by repeatedly applying `IndexFunction`:

```
c[0] = 0
c[i+1] = smallest index > c[i] + 1 where V[index - 1] == 1
          (or stop if no such index exists)
```

Then `D[i] = tau[c[i+1]]` for `i = 1 .. γ`, using the `tau` sequence from step 2a.

##### 5b – Parameter α (alpha)

`ParameterAlpha.calculate(V)` = `tau.last() + 1`

This is the total "span" of the vector in tau-space plus one.

##### 5c – Progression Parameters (α*, γ*)

`ProgressionParameters.calculate(D, α)` finds two values `(alphaStar, gammaStar)` that describe the arithmetic structure of D relative to α.

```
for each factor f of gcd(|D|, α), in descending order:
    B_candidate = α / f
    r = count of elements in D that are ≤ B_candidate
    if D[r .. r+|D|-1] (in the extended D ++ D+α) == D.map { it + B_candidate }:
        return (alphaStar = B_candidate, gammaStar = r)
```

The "extended E" is `D ++ D.map { it + α }`, which lets the shifted comparison wrap around.

##### 5d – Least Solution (x, y)

`LeastSolution.calculate(alphaStar, B)` finds the smallest positive integer solution to the congruence:

```
g = gcd(alphaStar, B)
x = B / g
y = alphaStar / g
```

##### 5e – Combine into new (j, B)

```
new_j = 2 * x * gammaStar  +  y * j
new_B = y * B  +  new_j
```

The updated `(j, B)` carries the period information one level back up toward the original vector.

### Final result

After all `p` iterations of the upward walk, `B` is the **minimal period** of the original register described by V and p.

```
return B
```

### Summary as pseudocode

```
function findMinimalPeriod(V0, p):
    assert p + 1 <= hMax(V0)

    // Contract downward
    V[0] = V0
    for i in 0 until p:
        V[i+1] = contract(V[i])

    // Base case at deepest level
    Vp_ext      = extend(V[p])           // increment last element
    (j, B)      = cyclicParameters(Vp_ext)

    // Unwind upward
    for n in p-1 downTo 0:
        (j, B) = algorithmicFunction(V[n], j, B)

    return B
```

---

## Verification

The algorithms are verified at three levels: step-by-step inspection of a single calculation, random sampling across many vectors, and exhaustive coverage of all vectors within a bounded parameter space.

### PeriodCalculationReport – Inspecting a single calculation

`PeriodCalculationReport` runs the analytical algorithm on one concrete vector and prints the full intermediate reasoning, organised into four sections:

- **Main results** – the computed minimal period
- **Contraction of Q** – how the vector is contracted level by level
- **Periodic parameters** – the cyclic parameters (ζ and j) at each level
- **Intermediate calculations** – every sub-step in detail

This makes it easy to follow the mathematics by hand and confirm that each step produces the expected output. It is particularly useful for understanding and validating a new or edge-case vector.

```sh
kotlin -cp target/symmetric-shift-register-1.0.0.jar PeriodCalculationReportKt
```

### RandomPeriodVerificationRunner – Sampling random vectors

`RandomPeriodVerificationRunner` generates a large number of random vectors and, for each one, computes the minimal period in **three independent ways**:

1. **Analytically** – using `AdjustmentOfParameters.findAllMinimalPeriods` (the full algorithm chain)
2. **By simulation** – using `Simulation`, which steps the register bit by bit until it returns to its initial state
3. **Via `AllCases`** – using the binary-representation path through `AllCases.findMinimalPeriod`

All three results are compared and the program throws an error immediately if any pair disagrees. A summary report is written to `reports/report*.txt`.

This provides broad statistical confidence that the analytical formula agrees with the direct simulation across a wide variety of inputs.

```sh
kotlin -cp target/symmetric-shift-register-1.0.0.jar verification.RandomPeriodVerificationRunnerKt
```

### ExhaustivePeriodVerificationRunner – Complete coverage within bounds

`ExhaustivePeriodVerificationRunner` takes the verification further by generating **all possible vectors** within configurable bounds (vector length and maximum element value), leaving no untested case within those limits.

For each vector and each valid value of `p`, it computes the period both analytically and by simulation and verifies they match. The results are printed in a tabular format showing `OK` or `FAIL` for every case, with a summary line at the end.

```sh
kotlin -cp target/symmetric-shift-register-1.0.0.jar ExhaustivePeriodVerificationRunnerKt
```

### JUnit tests

Each algorithmic module (`part1` through `part8`, `api`, `simulation`, `startvector`, etc.) has a dedicated JUnit 5 test class covering its individual functions with known inputs and expected outputs. These tests catch regressions at the unit level and serve as living documentation of each function's contract.

```sh
mvn test
```

---

## Installation and Usage

The project uses Maven and Kotlin. To build and run:

Recommended toolchain: **Kotlin 1.7.10** and **Java 8+** (compiled with JVM target `1.8`).

```sh
mvn clean install
```

To run the main application (generates a report):

```sh
kotlin -cp target/symmetric-shift-register-1.0.0.jar ExhaustivePeriodVerificationRunnerKt
```

## API Usage

Example usage of `SymmetricShiftRegisterApi`:

```kotlin
val api = SymmetricShiftRegisterApi()
val V = listOf(2, 1, 1, 4, 2, 1, 1, 3)

// Find valid range of p, then compute minimal period
val pRange = api.findValidPRange(V)          // e.g. ValidPRangeApiData(min=0, max=2)
val p = pRange.min                           // use p = 0
val period = api.findMinimalPeriodGeneralCase(V, p)  // e.g. 8

// Convert between representations
val binary = api.toBinaryRepresentation(V)           // [1,1,0,1,0,0,0,0,1,1,0,1,0,0,0]
val vector = api.toVectorRepresentation(binary)      // [2,1,1,4,2,1,1,3]

// Find a suitable k, then simulate
val k = api.findSuitableK(V, p)                      // e.g. 5
val simulatedPeriod = api.findMinimalPeriodByIteratingRegister(V, p, k)  // e.g. 8

// Generate a random valid set of parameters
val params = api.generateValidParameters()   // ValidParametersResponse(V, p, k)
```


## Testing

The project includes comprehensive tests (JUnit 5/kotlin.test). Run all tests with:

```sh
mvn test
```

## Dependencies

- Kotlin 1.7.10
- Java 8+ (JVM target 1.8)
- Maven
- JUnit 5 (for tests)

## License

See LICENSE (if available).
