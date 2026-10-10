package relativeweigth

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import relativeweight.IndirectMethod

internal class IndirectMethodTest {

    @Test
    fun testFindNewVectorAndParameters() {
        val q = listOf(1, 2, 3, 2)
        val p = 4
        val k = 2

        val result = IndirectMethod.findNewVectorAndParameters(q, p, k)

        assertEquals(3, result.p)
        assertEquals(2, result.k)
        assertEquals(listOf(3, 2, 3, 0), result.v)
    }

}

