package part1

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class InternalIndexFunctionTest {

    @Test
    fun `calculate - case 1`() {
        val v = listOf(2, 4, 1, 2, 1, 3, 1, 3, 3, 2, 4, 4, 2, 4)

        assertEquals(3, InternalIndexFunction.calculate(v, rStart = 1))
        assertEquals(0, InternalIndexFunction.calculate(v, rStart = 10))
    }

    @Test
    fun `calculate - case 2`() {
        val v = listOf(2, 1, 2, 2, 2, 3, 1, 3, 3, 2, 1, 1, 2, 1)

        assertEquals(1, InternalIndexFunction.calculate(v, rStart = 0))
        assertEquals(0, InternalIndexFunction.calculate(v, rStart = 3))
        assertEquals(1, InternalIndexFunction.calculate(v, rStart = 10))
    }
}