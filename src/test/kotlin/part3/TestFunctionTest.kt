package part3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TestFunctionTest {

    @Test
    fun `calculate - case 1 - returns 0 when condition not met`() {
        val D = listOf(2, 4, 8, 10, 14, 16)
        assertEquals(0, TestFunction.calculate(D, B = 3, r = 1))
    }

    @Test
    fun `calculate - case 2 - returns 1 when condition is met`() {
        val D = listOf(2, 4, 8, 10, 14, 16)
        assertEquals(1, TestFunction.calculate(D, B = 6, r = 2))
    }

    @Test
    fun `calculate - case 3`() {
        val D = listOf(1, 6, 11)
        assertEquals(1, TestFunction.calculate(D, B = 5, r = 1))
    }

    @Test
    fun `calculate - case 4`() {
        val D = listOf(1, 6, 10)
        assertEquals(0, TestFunction.calculate(D, B = 5, r = 1))
    }

}