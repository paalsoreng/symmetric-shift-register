package part7

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import part6.BinaryRepresentation

internal class NecessaryAssumptionsTest {

    @Test
    fun `validate - case 1`() {
        val V = listOf(3, 1, 2, 2, 2, 8, 1, 1)
        val binary = BinaryRepresentation.calculate(V)

        NecessaryAssumptions.validate(binary, k = 4, p = 3)
    }

    @Test
    fun `validate - throws when p is too high`() {
        val V = listOf(3, 1, 2, 2, 2, 8, 1, 1)
        val binary = BinaryRepresentation.calculate(V)

        assertThrows(IllegalArgumentException::class.java) {
            NecessaryAssumptions.validate(binary, k = 4, p = 4)
        }
    }
}