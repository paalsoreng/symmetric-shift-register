package part4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class IndexFunctionTest {

    val V = listOf(2, 1, 2, 2, 2, 3, 1, 3, 3, 2, 1, 1, 2, 3)

    @Test
    fun `calculate - case 1`() {
        assertEquals(2, IndexFunction.calculate(V, 0))
    }

    @Test
    fun `calculate - case 2`() {
        assertEquals(7, IndexFunction.calculate(V, 1))
    }

    @Test
    fun `calculate - case 3`() {
        assertEquals(7, IndexFunction.calculate(V, 2))
    }

    @Test
    fun `calculate - case 4`() {
        assertEquals(11, IndexFunction.calculate(V, 7))
    }

    @Test
    fun `calculate - case 5`() {
        assertEquals(0, IndexFunction.calculate(V, 11))
    }
}