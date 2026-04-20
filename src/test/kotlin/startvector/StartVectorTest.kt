package startvector

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import part6.GeneralCase
import simulation.Simulation

internal class StartVectorTest {

    @Test
    fun `should find same period with start vectors`() {
        val startA = listOf(0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1)
        val k = 2
        val p = 3

        val validStartValue = StartVector.calculate(startA, k, p)

        val minimalPeriodByMathematics = GeneralCase.findMinimalPeriod(
            Vstart = validStartValue.v,
            p = validStartValue.p,
        )

        val minimalPeriodByForceAndIteration = Simulation.findMinimalPeriodForBinary(startA, p = p, k = k)

        assertEquals(minimalPeriodByMathematics, minimalPeriodByForceAndIteration.toLong())
        assertEquals(AllCases.findMinimalPeriod(startA, p = p), minimalPeriodByForceAndIteration.toLong())
    }

    @Test
    fun `should find same period with a lot of cases`() {

        repeat(100000) {
            val binary = findRandomBinary(n = 20)
            val p = (0..5).random()
            val k = StartVector.findValidK(binary, p)

            try {
                val periodMath = AllCases.findMinimalPeriod(binary, p)
                val periodSimulation = Simulation.findMinimalPeriodForBinary(binary = binary, p = p, k = k)
                assertEquals(periodMath, periodSimulation.toLong())
            } catch (ex: Exception) {
                println(ex.message)
                println("binary: $binary, p: $p, k: $k")
                throw ex
            }
        }
    }

    private fun findRandomBinary(n: Int = 15): List<Int> {
        val list = MutableList(n) { (0..1).random() }
        if (list.none { it == 1 }) {
            list[(0 until n).random()] = 1
        }
        return list
    }
}