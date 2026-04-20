package verification

import java.io.File
import part6.BinaryRepresentation
import part7.AdjustmentOfParameters
import part7.PeriodResponse
import simulation.Simulation
import startvector.AllCases
import testdata.RandomVector

/**
 * - Generates test vectors
 * - Finds minimal periods in two ways:
 *   - With algorithms for symmetric shift register
 *   - With brute power (simulation)
 * - Compares the results (should be equal)
 * - Writes result to file report*.txt
 */
fun main() {

    val report = StringBuffer()

    var vectorHighest = mutableListOf<Int>()
    var periodHighest = PeriodResponse(0, 0, 0L)

    var count = 0
    val vectors = RandomVector.generateRandomVectorList(1000, 8, 5)

    for (v in vectors) {
        val periodResponses = AdjustmentOfParameters.findAllMinimalPeriods(v)

        report.appendLine(v.toString())

        for (pr in periodResponses) {
            val simulatedPeriod = Simulation.findMinimalPeriod(v, pr.p, pr.k)

            report.appendLine("  p=${pr.p} k=${pr.k} calc=${pr.minimalPeriod} sim=$simulatedPeriod")

            if (simulatedPeriod.toLong() != pr.minimalPeriod) {
                throw IllegalStateException("Calculation and simulation not identical.")
            }

            val allCasesPeriod = AllCases.findMinimalPeriod(BinaryRepresentation.calculate(v), pr.p)

            report.appendLine("  p=${pr.p} k=${pr.k} calc=${pr.minimalPeriod} all_case=$allCasesPeriod")

            if (allCasesPeriod != pr.minimalPeriod) {
                throw IllegalStateException("General case and all case not identical.")
            }

            count++

            if (pr.minimalPeriod > periodHighest.minimalPeriod) {
                vectorHighest = v.toMutableList()
                periodHighest = pr
            }
        }
        report.appendLine()
    }

    val header = StringBuffer()
    header.appendLine("Finished calculation and simulation of $count periods. Calculation and simulation were identical for all cases. ")
    header.appendLine("Highest period found: $periodHighest for:")
    header.appendLine("  V = $vectorHighest")
    header.appendLine("  A = ${BinaryRepresentation.calculate(vectorHighest).joinToString(separator = "")}")
    header.appendLine()

    val reportFile = nextReportFile("reports")
    reportFile.writeText(header.toString() + report.toString())
    println("Report written to ${reportFile.name}")
}

fun nextReportFile(directory: String): File {
    val dir = File(directory)
    val existing = dir.listFiles { f -> f.name.matches(Regex("report\\d*\\.txt")) }
        ?.mapNotNull { f ->
            val match = Regex("report(\\d*)\\.txt").matchEntire(f.name)
            val num = match?.groupValues?.get(1)
            if (num != null) if (num.isEmpty()) 1 else num.toInt() else null
        }
        ?.toSet() ?: emptySet()

    val next = generateSequence(1) { it + 1 }.first { it !in existing }
    val name = if (next == 1) "report.txt" else "report$next.txt"
    return File(directory, name)
}
