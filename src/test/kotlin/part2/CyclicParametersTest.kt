package part2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CyclicParametersTest {

    @Test
    fun `calculate - case 1`() {
        val v = listOf(2, 1, 1, 4, 2, 1, 1, 4)
        val cyclicParameters = CyclicParameters.calculate(v)

        assertEquals(4, cyclicParameters.j)
        assertEquals(8, cyclicParameters.B)
    }

    @Test
    fun `calculate - case 2`() {
        val v = listOf(1, 1, 1, 1001)
        val cyclicParameters = CyclicParameters.calculate(v)

        assertEquals(4, cyclicParameters.j)
        assertEquals(1004, cyclicParameters.B)
    }
}