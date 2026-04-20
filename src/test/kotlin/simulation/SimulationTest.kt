package simulation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import simulation.Simulation.shift

internal class SimulationTest {

    @Test
    fun `findMinimalPeriodForBinary - case 1`() {
        val binary = listOf(1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0)
        val period = Simulation.findMinimalPeriodForBinary(binary, p = 0, k = 5)
        assertEquals(8, period)
    }

    @Test
    fun `findMinimalPeriodForBinary - case 2`() {
        val binary = listOf(1, 1, 0, 0, 0, 1, 0, 0, 0)
        val period = Simulation.findMinimalPeriodForBinary(binary, p = 1, k = 1)
        assertEquals(68, period)
    }

    @Test
    fun `findMinimalPeriodForBinary - case 3`() {
        val binary = listOf(1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1)
        val period = Simulation.findMinimalPeriodForBinary(binary, p = 2, k = 3)
        assertEquals(982, period)
    }

    @Test
    fun `shift - case 1`() {
        assertEquals(
            listOf(1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
            shift(listOf(1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0), p = 1, k = 3)
        )
    }

    @Test
    fun `shift - case 2`() {
        assertEquals(
            listOf(1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
            shift(listOf(1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0), p = 1, k = 3)
        )
    }

    @Test
    fun `shift - case 3`() {
        assertEquals(
            listOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1),
            shift(listOf(1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0), p = 1, k = 3)
        )
    }
}