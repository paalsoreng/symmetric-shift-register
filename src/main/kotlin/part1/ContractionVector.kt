package part1

import trace.ContractionVectorExecution
import trace.ExecutionLog

object ContractionVector {

    fun calculate(v: List<Int>, log: ExecutionLog? = null): List<Int> {

        val cvLog = ContractionVectorExecution()
        log?.contractionExecutionList?.add(cvLog)

        val tau = Tau.calculate(v)
        val r = DecompositionParameters.calculate(v)
        val pi = mutableListOf<Int>()
        val j = r.size - 1

        for (i in 0 until j) {
            val value = tau[r[i + 1]] - tau[r[i]]
            pi.add(value)
        }

        val last = j - 1
        pi[last] += 1

        cvLog.pi = pi
        cvLog.r = r
        cvLog.tau = tau

        return pi
    }
}