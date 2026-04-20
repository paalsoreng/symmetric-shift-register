package part3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ProgressionParametersTest {

    @Test
    fun `calculate - case 1`() {
        assertEquals(
            ProgressionParametersResponse(5, 1),
            ProgressionParameters.calculate(D = listOf(1, 6, 11), α = 15)
        )
    }

    @Test
    fun `calculate - case 2`() {
        assertEquals(
            ProgressionParametersResponse(6, 2),
            ProgressionParameters.calculate(D = listOf(2, 4, 8, 10, 14, 16), α = 18)
        )
    }

    @Test
    fun `calculate - case 3`() {
        assertEquals(
            ProgressionParametersResponse(5, 2),
            ProgressionParameters.calculate(D = listOf(1, 4, 6, 9, 11, 14, 16, 19), α = 20)
        )
    }

    @Test
    fun `calculate - case 4`() {
        assertEquals(
            ProgressionParametersResponse(10, 4),
            ProgressionParameters.calculate(D = listOf(1, 4, 4, 9, 11, 14, 14, 19), α = 20)
        )
    }
}