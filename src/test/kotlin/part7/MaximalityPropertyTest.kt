package part7

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MaximalityPropertyTest {

    @Test
    fun `calculate - case 1`() {
        val V = listOf(3, 1, 2, 2, 2, 8, 1, 1)

        val (t, hMax) = MaximalityProperty.calculate(V)

        assertEquals(5, t)
        assertEquals(4, hMax)
    }
}