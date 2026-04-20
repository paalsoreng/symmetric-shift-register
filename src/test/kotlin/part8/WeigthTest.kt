package part8

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class WeigthTest {

    @Test
    fun `calculate - case 1`() {
        val V = listOf(2, 3, 1, 6, 7, 3)

        val w = Weigth.calculate(V)

        assertEquals(10, w)
    }
}