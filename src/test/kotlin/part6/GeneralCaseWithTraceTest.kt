package part6

import org.junit.jupiter.api.Test
import trace.ExecutionLog
import trace.ReportPrinter
import trace.ReportBuilder

internal class GeneralCaseWithTraceTest {

    @Test
    fun `findMinimalPeriod - case 1`() {
        val V = listOf(3, 4, 2, 4, 1, 0)
        val p = 2

        val log = ExecutionLog()

        GeneralCase.findMinimalPeriod(V, p, log)

        val report = ReportBuilder.fromExecutionLogToReport(log)
        val text = ReportPrinter.fromReportToText(report)
        println(text)
    }
}