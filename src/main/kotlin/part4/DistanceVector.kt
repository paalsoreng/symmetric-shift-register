package part4

import part1.Tau
import trace.AlgorithmicFunctionExecution

object DistanceVector {

    fun calculate(V: List<Int>, afe: AlgorithmicFunctionExecution? = null): List<Int> {

        val c = CIndexes.calculate(V)
        val tau = Tau.calculate(V)

        val gamma = c.size - 1
        val D = mutableListOf<Int>()

        for (i in 1..gamma) {
            D.add(tau[c[i]])
        }

        afe?.tau = tau
        afe?.c = c

        return D
    }
}