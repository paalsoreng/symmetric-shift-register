package part5

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ParameterAlphaTest {

    @Test
    fun `calculate - case 1`() {

        assertEquals(
            1003,
            ParameterAlpha.calculate(listOf(2, 2, 2, 2, 1, 999))
        )
    }
}