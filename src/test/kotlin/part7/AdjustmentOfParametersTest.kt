package part7

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AdjustmentOfParametersTest {

    val V = listOf(3, 1, 2, 2, 2, 8, 1, 1)

    @Test
    fun `findAllMinimalPeriods - case 1`() {
        val periodResponses = AdjustmentOfParameters.findAllMinimalPeriods(V)
        assertEquals(PeriodResponse(p = 0, k = 7, minimalPeriod = 21L), periodResponses[0])
    }

    @Test
    fun `findAllMinimalPeriods - case 2`() {
        val periodResponses = AdjustmentOfParameters.findAllMinimalPeriods(V)
        assertEquals(PeriodResponse(p = 1, k = 6, minimalPeriod = 290L), periodResponses[1])
    }

    @Test
    fun `findAllMinimalPeriods - case 3`() {
        val periodResponses = AdjustmentOfParameters.findAllMinimalPeriods(V)
        assertEquals(PeriodResponse(p = 2, k = 5, minimalPeriod = 3130L), periodResponses[2])
    }

    @Test
    fun `findAllMinimalPeriods - case 4`() {
        val periodResponses = AdjustmentOfParameters.findAllMinimalPeriods(V)
        assertEquals(PeriodResponse(p = 3, k = 4, minimalPeriod = 1680L), periodResponses[3])
    }
}