package part8

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class StartVectorAssumptionsTest {

    @Test
    fun `validate - case 1`() {
        val V = listOf(3, 1, 2, 4)
        val k = 4
        val p = 1

        StartVectorAssumptions.validate(V, k, p)
    }

    @Test
    fun `validate - throws when k is bigger than w`() {
        val V = listOf(3, 1, 2, 4)
        val k = 6
        val p = 1

        val error = assertThrows(IllegalArgumentException::class.java) {
            StartVectorAssumptions.validate(V, k, p)
        }
        assertTrue(error.message!!.contains("k < w(A) is not fulfilled"))
    }

    @Test
    fun `validate - throws when p is too small`() {
        val V = listOf(3, 1, 2, 4)
        val k = 1
        val p = 1

        val error = assertThrows(IllegalArgumentException::class.java) {
            StartVectorAssumptions.validate(V, k, p)
        }
        assertTrue(error.message!!.contains("w(A) <= k + p + 1 is not fulfilled"))
    }
}