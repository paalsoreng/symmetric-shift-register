package part8

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class InfiniteVectorRepresentationTest {

    @Test
    fun `calculate - case 1`() {

        val V = listOf(3, 1, 2, 4)
        val k = 4
        val p = 1

        val response = InfiniteVectorRepresentation.calculate(V, k, p, 4)
        val q = response.q
        val b = response.b
        val e = response.e

        assertEquals(
            listOf(3, 1, 2, 5, 3, 1, 3, 5),
            q
        )
        assertEquals(
            listOf(1, 0, 1, 0, 2, 0),
            b
        )
        assertEquals(
            listOf(4, 2, 0, 1, 3, 1),
            e
        )
    }
}