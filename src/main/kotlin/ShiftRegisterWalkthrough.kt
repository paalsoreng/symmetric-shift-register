import api.SymmetricShiftRegisterApi

/**
 * ShiftRegisterWalkthrough – a guided introduction to the symmetric shift register.
 *
 * A symmetric shift register is a binary register of length n.
 * At each step the register shifts left, and a new bit is appended on the right.
 * Whether that new bit is flipped or passed through depends on a weighted sum of the
 * interior bits and two parameters p and k.
 *
 * The key question: how long before the register returns to its initial state?
 * That length is called the **minimal period**.
 *
 * This walkthrough shows:
 *   1. How to define a vector V and a parameter p
 *   2. How to compute the minimal period analytically (using the theory)
 *   3. How to verify the period by step-by-step simulation
 *   4. How to print a step-by-step simulation trace
 */
fun main() {
    val api = SymmetricShiftRegisterApi()

    // ── Input ────────────────────────────────────────────────────────────────
    //
    // V is the vector representation of the register's initial state.
    // p is a parameter that controls the shift-and-flip rule (must be valid for V).
    //
    // V encodes a binary string as run-length segments:
    //   [2, 1, 1, 4, 2, 1, 1, 3]
    //   → 1 1 | 0 | 1 | 0 0 0 0 | 1 1 | 0 | 1 | 0 0 0
    //   (alternating runs of 1s and 0s, starting with 1s)
    //
    val V = listOf(2, 1, 1, 4, 2, 1, 1, 3)
    val p = 0

    val binary = api.toBinaryRepresentation(V)
    val k      = api.findSuitableK(V, p)

    println("═══════════════════════════════════════════════════════")
    println("  Symmetric Shift Register – Walkthrough")
    println("═══════════════════════════════════════════════════════")
    println()
    println("  Vector representation  V  = $V")
    println("  Binary representation  A  = $binary")
    println("  Parameter              p  = $p")
    println("  Derived parameter      k  = $k")
    println()

    // ── 1. Validate parameters ───────────────────────────────────────────────
    val range = api.findValidPRange(V)
    println("  Valid range for p: ${range.min} .. ${range.max}  (chosen p=$p ✓)")
    println()

    // ── 2. Compute minimal period analytically ───────────────────────────────
    val analyticalPeriod = api.findMinimalPeriodGeneralCase(V, p)
    println("  ┌─────────────────────────────────────────────────┐")
    println("  │  Minimal period (analytical formula) = $analyticalPeriod".padEnd(52) + "│")
    println("  └─────────────────────────────────────────────────┘")
    println()

    // ── 3. Verify by simulation ──────────────────────────────────────────────
    val simulatedPeriod = api.findMinimalPeriodByIteratingRegister(V, p, k)
    val match = if (analyticalPeriod == simulatedPeriod.toLong()) "✓ match" else "✗ MISMATCH"
    println("  Minimal period (simulation) = $simulatedPeriod  [$match]")
    println()

    // ── 4. Step-by-step trace ────────────────────────────────────────────────
    println("  Step-by-step simulation:")
    println("  ─────────────────────────────────────────────────")
    val trace = api.simulateWithExplanation(V, p, k)
    println(trace.prependIndent("  "))
}