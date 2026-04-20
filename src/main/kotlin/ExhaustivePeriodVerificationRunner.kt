import kotlin.math.pow
import verification.ExhaustiveVerification
import verification.nextReportFile

/**
 * Exhaustive period verification runner.
 *
 * Generates ALL possible vectors within the given parameter bounds,
 * computes the minimal period analytically and by direct simulation
 * (stepping the register one bit at a time until it returns to its initial state)
 * for each case, and verifies that both methods always agree.
 *
 * Unlike RandomPeriodVerificationRunner (which samples randomly),
 * this runner covers the entire parameter space up to the given limits –
 * leaving no untested cases within those bounds.
 */
fun main() {

    // --- Configuration ---

    // Minimum and maximum number of elements in the vectors to test.
    // A vector V = [v1, v2, ..., vn] has length n.
    // Increasing maxLen exponentially increases the number of vectors tested.
    val minLen = 2
    val maxLen = 4

    // Maximum value of each element in the vector.
    // Each element v_i will range from 1 to maxVal (inclusive).
    // Total number of vectors = maxVal^minLen + ... + maxVal^maxLen.
    val maxVal = 4

    // Set to true to write the full output to a timestamped report file in reports/.
    val writeReport = false

    // --- End configuration ---

    val totalVectors = (minLen..maxLen).sumOf { len ->
        maxVal.toDouble().pow(len.toDouble()).toLong()
    }

    println("=== ExhaustivePeriodVerificationRunner ===")
    println("Vector length : $minLen – $maxLen")
    println("Element range : 1 – $maxVal")
    println("Total vectors : $totalVectors (before filtering invalid p)")
    println()

    val report = StringBuffer()
    report.appendLine("Exhaustive period verification")
    report.appendLine("Vector length : $minLen – $maxLen")
    report.appendLine("Element range : 1 – $maxVal")
    report.appendLine()

    val result = ExhaustiveVerification.verify(
        minLen = minLen,
        maxLen = maxLen,
        maxVal = maxVal
    )

    // Compute column widths from actual data so all columns align neatly
    val wV          = result.cases.maxOfOrNull { "V=${it.V}".length }?.coerceAtLeast(4) ?: 4
    val wP          = result.cases.maxOfOrNull { "p=${it.p}".length }?.coerceAtLeast(4) ?: 4
    val wK          = result.cases.maxOfOrNull { "k=${it.k}".length }?.coerceAtLeast(4) ?: 4
    val wAnalytical = result.cases.maxOfOrNull { "analytical=${it.analytical}".length }?.coerceAtLeast(10) ?: 10

    for (case in result.cases) {
        val line = buildString {
            append(if (case.match) "OK  " else "FAIL")
            append("  "); append("V=${case.V}".padEnd(wV))
            append("  "); append("p=${case.p}".padEnd(wP))
            append("  "); append("k=${case.k}".padEnd(wK))
            append("  "); append("analytical=${case.analytical}".padEnd(wAnalytical))
            append("  "); append("simulated=${case.simulated}")
        }
        println(line)
        report.appendLine(line)
    }

    println()

    val summary = if (result.allMatch)
        "PASSED: ${result.total} cases verified, ${result.skipped} vectors skipped – all matched."
    else
        "FAILED: ${result.mismatches.size} mismatches out of ${result.total} cases!"

    println(summary)
    report.appendLine()
    report.appendLine(summary)

    if (!result.allMatch) {
        println()
        println("=== Mismatches ===")
        for (m in result.mismatches) {
            println("  V=${m.V}  p=${m.p}  k=${m.k}  analytical=${m.analytical}  simulated=${m.simulated}")
        }
    }

    if (writeReport) {
        val reportFile = nextReportFile("reports")
        reportFile.writeText(report.toString())
        println()
        println("Report written to ${reportFile.name}")
    }
}
