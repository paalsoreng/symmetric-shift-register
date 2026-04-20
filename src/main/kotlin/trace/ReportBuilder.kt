package trace

object ReportBuilder {

    fun fromExecutionLogToReport(log: ExecutionLog): Report {

        val inputParams = InputParams(
            p = log.p!!,
            v0 = log.V0!!
        )

        val contractList = contracts(log, inputParams)

        val stepList = mutableListOf<Step>()

        // step 0
        stepList.add(
            Step(
                v = log.V!!,
                j = log.j!!,
                zeta = log.B!!
            )
        )

        for (ae in log.algorithmicExecutionList) {
            val step = Step(
                j = ae.r!!,
                zeta = ae.beta!!,
                gamma = ae.gamma,
                v = ae.V!!,
                c = ae.c,
                tau = ae.tau,
                d = ae.D,
                alpha = ae.alpha,
                alphaStar = ae.alphaStar,
                gammaStar = ae.gammaStar,
                g = ae.g,
                x = ae.x,
                y = ae.y
            )
            stepList.add(step)
        }


        return Report(
            inputParams,
            contractList,
            stepList
        )
    }

    private fun contracts(
        log: ExecutionLog,
        inputParams: InputParams
    ): MutableList<Contract> {

        if (log.contractionExecutionList.isEmpty()) {
            return mutableListOf(
                Contract(
                    pi = inputParams.v0,
                    r = emptyList(),
                    tau = emptyList()
                )
            )
        }

        val contractList = mutableListOf<Contract>()

        for (i in log.contractionExecutionList.indices) {
            if (i == 0) {
                val execution = log.contractionExecutionList[i]
                val contract = Contract(
                    pi = inputParams.v0,
                    r = execution.r!!,
                    tau = execution.tau!!
                )
                contractList.add(contract)
            } else {
                val execution = log.contractionExecutionList[i]
                val preExecution = log.contractionExecutionList[i - 1]
                val contract = Contract(
                    pi = preExecution.pi!!,
                    r = execution.r!!,
                    tau = execution.tau!!
                )
                contractList.add(contract)
            }
        }
        contractList.add(
            Contract(
                pi = log.contractionExecutionList.last().pi!!,
                r = listOf(),
                tau = listOf(),
            )
        )
        return contractList
    }
}