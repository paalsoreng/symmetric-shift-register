package part6

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ExtensionVectorTest {

    @Test
    fun `calculate - case 1`() {
        assertEquals(
            listOf(1, 1, 3, 2, 2, 5),
            ExtensionVector.calculate(listOf(1, 1, 3, 2, 2, 4))
        )
    }
}