package part6

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import simulation.Simulation

internal class GeneralCaseTest {

    @Test
    fun `findMinimalPeriod - case 1`() {
        val V = listOf(2, 1, 1, 4, 2, 1, 1, 3)
        val p = 0
        val k = 5

        val calculatedPeriod = GeneralCase.findMinimalPeriod(V, p)
        val simulatedPeriod = Simulation.findMinimalPeriod(V, p, k)

        assertEquals(8L, calculatedPeriod)
        assertEquals(8, simulatedPeriod)
    }

    @Test
    fun `findMinimalPeriod - case 2`() {
        val V = listOf(2, 3, 1, 3)
        val p = 1
        val k = 1

        val calculatedPeriod = GeneralCase.findMinimalPeriod(V, p)
        val simulatedPeriod = Simulation.findMinimalPeriod(V, p, k)

        assertEquals(68L, calculatedPeriod)
        assertEquals(68, simulatedPeriod)
    }

    @Test
    fun `findMinimalPeriod - case 3`() {
        val V = listOf(3, 4, 2, 4, 1, 0)
        val p = 2
        val k = 3

        val calculatedPeriod = GeneralCase.findMinimalPeriod(V, p)
        val simulatedPeriod = Simulation.findMinimalPeriod(V, p, k)

        assertEquals(982L, calculatedPeriod)
        assertEquals(982, simulatedPeriod)
    }

    @Test
    fun `findMinimalPeriod - case 4`() {
        val V = listOf(3, 3, 3, 3, 2, 2, 1, 998)
        val p = 2

        val calculatedPeriod = GeneralCase.findMinimalPeriod(V, p)

        assertEquals(128653810L, calculatedPeriod)
    }

    @Test
    fun `findMinimalPeriod - case 5`() {
        val V = listOf(5, 2, 4, 1, 5, 3, 5, 5)
        val p = 10
        val k = 8

        val calculatedPeriod = GeneralCase.findMinimalPeriod(V, p)
        val simulatedPeriod = Simulation.findMinimalPeriod(V, p, k)

        assertEquals(295530L, calculatedPeriod)
        assertEquals(295530, simulatedPeriod)
    }
}