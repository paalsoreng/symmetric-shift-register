package part5

import part3.GreatestCommonDivisor
import trace.AlgorithmicFunctionExecution

object LeastSolution {

    fun calculate(α: Int, β: Long, afe: AlgorithmicFunctionExecution? = null): Pair<Long, Long> {

        val g = GreatestCommonDivisor.calculate(α.toLong(), β)
        afe?.g = g

        val x = β / g
        val y = α / g

        return Pair(x, y)
    }
}