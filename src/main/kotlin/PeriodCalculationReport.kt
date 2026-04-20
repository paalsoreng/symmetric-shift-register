import api.SymmetricShiftRegisterApi

/**
 * PeriodCalculationReport – shows the full analytical calculation behind the period.
 *
 * Uses a vector V with a higher p value to show a richer report with multiple contraction levels.
 * Calls buildStructuredReport and prints the step-by-step reasoning:
 *
 *   • Main results    – the minimal period (the answer)
 *   • Contraction     – how Q is contracted down to Q[0], level by level
 *   • Periodic params – the periodic parameters (zeta and j) at each level
 *   • Intermediate    – the detailed intermediate calculations at each step
 *
 * This is the mathematical engine that makes the analytical formula work.
 */
fun main() {
    val api = SymmetricShiftRegisterApi()

    val V = listOf(5, 1, 3, 4, 1, 2)
    val p = 6

    println("═══════════════════════════════════════════════════════")
    println("  Symmetric Shift Register – Calculation Report")
    println("═══════════════════════════════════════════════════════")
    println()
    println("  Input:  V = $V,  p = $p")
    println()

    val report = api.buildStructuredReport(V, p)

    println("  ── Main Results ────────────────────────────────────")
    println(report.mainResults.prependIndent("  "))
    println()

    println("  ── Contraction of Q ────────────────────────────────")
    println(report.contractionResults.prependIndent("  "))
    println()

    println("  ── Periodic Parameters ─────────────────────────────")
    println(report.periodicResults.prependIndent("  "))
    println()

    println("  ── Intermediate Calculations ───────────────────────")
    println(report.intermediateResults.prependIndent("  "))
}