package part4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DistanceVectorTest {

    @Test
    fun `calculate - case 1`() {
        val V = listOf(2, 1, 2, 2, 2, 3, 1, 3, 3, 2, 1, 1, 2, 3)

        assertEquals(
            listOf(1, 6, 11),
            DistanceVector.calculate(V)
        )
    }

    @Test
    fun `calculate - case 2`() {
        val V = listOf(3, 3, 3, 3, 2, 2, 1, 998)

        assertEquals(
            listOf(10),
            DistanceVector.calculate(V)
        )
    }

    @Test
    fun `calculate - case 3`() {
        val V = listOf(2, 2, 2, 2, 1, 999)

        assertEquals(
            listOf(4),
            DistanceVector.calculate(V)
        )
    }
}