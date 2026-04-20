package trace

object ReportPrinter {

    fun fromReportToText(report: Report): String {
        val sb = StringBuilder()
        sb.appendLine("Input Params:")
        sb.appendLine("  p: ${report.inputParams.p}")
        sb.appendLine("  v0: ${report.inputParams.v0}")

        sb.appendLine("\nContracts:")
        report.contractList.forEachIndexed { idx, contract ->
            sb.appendLine("  Contract $idx:")
            sb.appendLine("    pi: ${contract.pi}")
            sb.appendLine("    tau: ${contract.tau}")
            sb.appendLine("    r: ${contract.r}")
        }

        sb.appendLine("\nSteps:")
        report.stepList.forEachIndexed { idx, step ->
            sb.appendLine("  Step $idx:")
            sb.appendLine("    v: ${step.v}")
            sb.appendLine("    j: ${step.j}")
            sb.appendLine("    beta: ${step.zeta}")
            sb.appendLine("    gamma: ${step.gamma}")
            sb.appendLine("    c: ${step.c}")
            sb.appendLine("    tau: ${step.tau}")
            sb.appendLine("    d: ${step.d}")
            sb.appendLine("    alpha: ${step.alpha}")
            sb.appendLine("    alphaStar: ${step.alphaStar}")
            sb.appendLine("    gammaStar: ${step.gammaStar}")
            sb.appendLine("    g: ${step.g}")
            sb.appendLine("    x: ${step.x}")
            sb.appendLine("    y: ${step.y}")
        }
        return sb.toString()
    }



}