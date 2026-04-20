package part1

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class DecompositionParametersTest {

    @Test
    fun `calculate - case 1`() {
        val v = listOf(2, 1, 2, 2, 2, 3, 1, 3, 3, 2, 1, 1, 2, 3)

        assertEquals(
            listOf(0, 3, 4, 5, 8, 9, 12, 13, 14),
            DecompositionParameters.calculate(v)
        )
    }
}