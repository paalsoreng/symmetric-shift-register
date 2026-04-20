package part5

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class AlgorithmicFunctionTest {

    @Test
    fun `V with ones only in interior is detected`() {
        assertTrue(listOf(1, 2, 2, 2, 1).hasNoOnesInInterior()) // does not care for first or last in list
        assertFalse(listOf(1, 2, 1, 2, 1).hasNoOnesInInterior())
    }

    @Test
    fun `calculate - case 1`() {
        val V = listOf(2, 1, 2, 2, 2, 3, 1, 3, 3, 2, 1, 1, 2, 3)
        val jStar = 4L
        val betaStar = 8L

        val (r, beta) = AlgorithmicFunction.calculate(V, jStar, betaStar)
        assertEquals(36L, r)
        assertEquals(76L, beta)
    }

    @Test
    fun `calculate - case 2`() {
        val V = listOf(3, 2, 2, 5, 3, 2, 2, 3)
        val jStar = 4L
        val betaStar = 8L

        val (r, beta) = AlgorithmicFunction.calculate(V, jStar, betaStar)
        assertEquals(4L, r)
        assertEquals(12L, beta)
    }

    @Test
    fun `calculate - case 3`() {
        val V = listOf(2, 2, 2, 2, 1, 999)
        val jStar = 4L
        val betaStar = 1004L

        val (r, beta) = AlgorithmicFunction.calculate(V, jStar, betaStar)
        assertEquals(6020L, r)
        assertEquals(1013032L, beta)
    }

    @Test
    fun `calculate - case 4`() {
        val V = listOf(3, 3, 3, 3, 2, 2, 1, 998)
        val jStar = 6020L
        val betaStar = 1013032L

        val (r, beta) = AlgorithmicFunction.calculate(V, jStar, betaStar)
        assertEquals(1011778L, r)
        assertEquals(128653810L, beta)
    }

    @Test
    fun `calculate - case 5`() {
        val V = listOf(3, 4, 2, 4, 1, 0)
        val jStar = 26L
        val betaStar = 68L

        val (r, beta) = AlgorithmicFunction.calculate(V, jStar, betaStar)
        assertEquals(370L, r)
        assertEquals(982L, beta)
    }

    @Test
    fun `calculate - case 6`() {
        val V = listOf(2, 3, 1, 3)
        val jStar = 2L
        val betaStar = 7L

        val (r, beta) = AlgorithmicFunction.calculate(V, jStar, betaStar)
        assertEquals(26L, r)
        assertEquals(68L, beta)
    }
}