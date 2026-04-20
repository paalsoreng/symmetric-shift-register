package part3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AuxiliaryFunctionTest {

    @Test
    fun `calculate - case 1`() {
        val D = listOf(1, 6, 9)
        val alpha = 5
        val expected = 1 // 5 and 10 are <= 10
        assertEquals(expected, AuxiliaryFunction.calculate(D, alpha))
    }
}
