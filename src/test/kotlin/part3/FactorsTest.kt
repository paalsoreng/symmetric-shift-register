package part3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class FactorsTest {

    @Test
    fun `calculate - case 1`() {
        assertEquals(
            listOf(12, 6, 4, 3, 2, 1),
            Factors.calculate(12)
        )
    }

    @Test
    fun `calculate - case 2`() {
        assertEquals(
            listOf(9, 3, 1),
            Factors.calculate(9)
        )
    }
}