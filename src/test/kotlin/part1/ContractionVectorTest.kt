package part1

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class ContractionVectorTest {

    @Test
    fun `calculate - case 1`() {
        assertEquals(
            listOf(2, 1, 1, 4, 2, 1, 1, 3),
            ContractionVector.calculate(listOf(2, 1, 2, 2, 2, 3, 1, 3, 3, 2, 1, 1, 2, 3))
        )
    }
}