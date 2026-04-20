package part5

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LeastSolutionTest {

    @Test
    fun `calculate - case 1`() {
        val (x, y) = LeastSolution.calculate(α = 5, β = 8)
        assertEquals(8, x)
        assertEquals(5, y)
    }

    @Test
    fun `calculate - case 2`() {
        val (x, y) = LeastSolution.calculate(α = 1003, β = 1004)
        assertEquals(1004, x)
        assertEquals(1003, y)
    }

    @Test
    fun `calculate - case 3`() {
        val (x, y) = LeastSolution.calculate(α = 1008, β = 1013032)
        assertEquals(126629, x)
        assertEquals(126, y)
    }
}