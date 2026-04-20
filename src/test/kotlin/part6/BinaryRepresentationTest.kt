package part6
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
internal class BinaryRepresentationTest {
    @Test
    fun `calculate - case 1`() {
        val V = listOf(3, 4, 2, 4, 1, 0)
        assertEquals(
            listOf(1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1),
            BinaryRepresentation.calculate(V)
        )
    }
    @Test
    fun `calculate - case 2 - roundtrip through VectorRepresentation`() {
        val V = listOf(3, 4, 2, 4, 1, 0)
        assertEquals(
            listOf(1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1),
            BinaryRepresentation.calculate(
                VectorRepresentation.calculate(
                    BinaryRepresentation.calculate(V)
                )
            )
        )
    }
}
