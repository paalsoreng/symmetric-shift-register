package part6

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class VectorRepresentationTest {

    @Test
    fun `calculate - case 1`() {
        assertEquals(
            listOf(2, 2, 3, 2),
            VectorRepresentation.calculate(listOf(1, 1, 0, 0, 1, 1, 1, 0, 0))
        )
    }

    @Test
    fun `calculate - case 2`() {
        assertEquals(
            listOf(4, 2, 3, 0),
            VectorRepresentation.calculate(listOf(1, 1, 1, 1, 0, 0, 1, 1, 1))
        )
    }

    @Test
    fun `calculate - case 3`() {
        assertEquals(
            listOf(2,1,1,4,2,1,1,3),
            VectorRepresentation.calculate(listOf(1,1,0,1,0,0,0,0,1,1,0,1,0,0,0))
        )
    }
}