package relativeweigth

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import relativeweight.DirectMethod

internal class DirectMethodTest {

    @Test
    fun testFindPeriod() {

        val V = listOf(2, 1, 10, 10, 1, 10)
        val p = 10
        val k = 5

        val period = DirectMethod.findPeriod(V, p, k)

        assertEquals(1345, period)
    }

}