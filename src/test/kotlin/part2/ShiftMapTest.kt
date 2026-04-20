package part2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ShiftMapTest {

    @Test
    fun `calculate - case 1`() {
        assertEquals(
            listOf(1, 4, 2, 1, 1, 4, 2, 1),
            ShiftMap.calculate(listOf(2, 1, 1, 4, 2, 1, 1, 4), 2)
        )
    }

    @Test
    fun `calculate - case 2`() {
        assertEquals(
            listOf(4, 2, 1, 1, 4, 2, 1, 1),
            ShiftMap.calculate(listOf(1, 4, 2, 1, 1, 4, 2, 1), 1)
        )
    }
}