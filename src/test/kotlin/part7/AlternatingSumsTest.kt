package part7

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AlternatingSumsTest {

    @Test
    fun `calculate - case 1`() {

        val V = listOf(3, 1, 2, 1, 4, 0)

        assertEquals(
            listOf(0, 3, 2, 4, 3, 7, 7),
            AlternatingSums.calculate(V)
        )
    }
}