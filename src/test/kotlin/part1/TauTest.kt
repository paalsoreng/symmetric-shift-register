package part1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TauTest {

    @Test
    fun `calculate - case 1`() {
        val v = listOf(2, 3, 4, 5)

        val expected = listOf(0, 1, 3, 6, 10)
        val actual = Tau.calculate(v)

        assertEquals(expected, actual)
    }

    @Test
    fun `calculate - case 2`() {
        val v = listOf(2, 1, 2, 2, 2, 3, 1, 3, 3, 2, 1, 1, 2, 3)

        assertEquals(
            listOf(0, 1, 1, 2, 3, 4, 6, 6, 8, 10, 11, 11, 11, 12, 14),
            Tau.calculate(v)
        )
    }
}