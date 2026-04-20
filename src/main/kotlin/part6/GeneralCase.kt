package part6

import part1.ContractionVector
import part2.CyclicParameters
import part5.AlgorithmicFunction

import part7.MaximalityProperty

import trace.ExecutionLog

object GeneralCase {

    fun findMinimalPeriod(Vstart: List<Int>, p: Int, log: ExecutionLog? = null): Long {
        log?.p = p
        log?.V0 = Vstart

        val (_, hMax) = MaximalityProperty.calculate(Vstart)

        if (p + 1 > hMax) {
            throw IllegalArgumentException("p + 1 <= hMax is not fulfilled: p=$p, hMax=$hMax")
        }

        val V = mutableListOf(Vstart)

        for (i in 0 until p) {
            V += ContractionVector.calculate(V[i], log)
        }

        val Vp_ext = ExtensionVector.calculate(V.last())
        log?.Vp_ext = Vp_ext

        val (jp, Bp) = CyclicParameters.calculate(Vp_ext)

        var j: Long = jp.toLong()
        var B: Long = Bp
        log?.j = jp.toLong()
        log?.B = B

        for (n in p - 1 downTo 0) {
            val (jn, Bn) = AlgorithmicFunction.calculate(V[n], j, B, log)
            j = jn
            B = Bn
        }

        log?.V = V.last()

        return B
    }
}