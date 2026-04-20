package part8

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import part6.GeneralCase
import simulation.Simulation

internal class StartVectorTest {

    @Test
    fun `calculate - case 1`() {
        val V = listOf(3, 1, 2, 4)
        val k = 4
        val p = 1

        val startVector = StartVector.calculate(V, k, p)

        assertEquals(
            listOf(3, 1, 3, 3),
            startVector
        )
    }

    @Test
    fun `calculate - case 2 - period of start vector matches simulated period`() {
        val V = listOf(3, 1, 2, 4)
        val k = 4
        val p = 1

        val startVector = StartVector.calculate(V, k, p)

        // og her fungerer det å finne perioden
        val startVectorPeriod = GeneralCase.findMinimalPeriod(startVector, p)

        // Vi finner også perioden med brutal makt (simulering)
        val simulatedPeriod = Simulation.findMinimalPeriod(V, p, k)

        // Og forventer at disse er like
        assertEquals(
            startVectorPeriod,
            simulatedPeriod.toLong()
        )
    }
}