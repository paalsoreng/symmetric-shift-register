package part3

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class GreatestCommonDivisorTest {

    @Test
    fun `calculate - case 1`() {
        assertEquals(3, GreatestCommonDivisor.calculate(12, 9))
    }

    @Test
    fun `calculate - case 2`() {
        assertEquals(2, GreatestCommonDivisor.calculate(18, 4))
    }

    @Test
    fun `calculate - case 3`() {
        assertEquals(5, GreatestCommonDivisor.calculate(15, 10))
    }

    @Test
    fun `calculate - case 4 - argument order does not matter`() {
        assertEquals(5, GreatestCommonDivisor.calculate(10, 15))
    }

    @Test
    fun `calculate - case 5`() {
        assertEquals(4, GreatestCommonDivisor.calculate(8, 12))
    }

    @Test
    fun `calculate - case 6`() {
        assertEquals(5, GreatestCommonDivisor.calculate(10, 45))
    }
}