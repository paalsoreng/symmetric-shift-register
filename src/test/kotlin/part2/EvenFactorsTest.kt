package part2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class EvenFactorsTest {

    @Test
    fun `calculate - case 1`() {
        assertEquals(
            listOf(2, 4, 8, 16),
            EvenFactors.calculate(16)
        )
    }

    @Test
    fun `calculate - case 2`() {
        assertEquals(
            listOf(2, 6, 18),
            EvenFactors.calculate(18)
        )
    }

    @Test
    fun `calculate - case 3`() {
        assertEquals(
            listOf(2, 6, 18),
            EvenFactors.calculate(18)
        )
    }

    @Test
    fun `calculate - case 4`() {
        assertEquals(
            listOf(2, 4, 8),
            EvenFactors.calculate(8)
        )
    }
}