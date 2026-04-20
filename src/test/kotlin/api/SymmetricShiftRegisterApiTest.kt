package api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import part6.VectorRepresentation

class SymmetricShiftRegisterApiTest {
    private val api = SymmetricShiftRegisterApi()

    @Test
    fun findMinimalPeriodForVector_ByIteratingRegister_works() {
        val V = listOf(2, 3, 1, 3)
        val p = 1
        val k = 1
        val period = api.findMinimalPeriodByIteratingRegister(V, p, k)
        assertEquals(68, period)
    }

    @Test
    fun findMinimalPeriodForAllCases_works() {
        val binary = listOf(1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1)
        val v = VectorRepresentation.calculate(binary)
        val p = 2
        val period = api.findMinimalPeriodForAllCases(v, p)
        assertEquals(982L, period)
    }

    @Test
    fun findSuitableK_typicalCase() {
        val V = listOf(3, 1, 2, 2, 2, 8, 1, 1)
        val p = 0
        val k = api.findSuitableK(V, p)
        assertEquals(7, k)
    }

    @Test
    fun findSuitableK_anotherCase() {
        val V = listOf(2, 3, 1, 3)
        val p = 1
        val k = api.findSuitableK(V, p)
        assertEquals(1, k)
    }

    @Test
    fun toVectorRepresentation_works() {
        assertEquals(
            listOf(2, 2, 3, 2),
            api.toVectorRepresentation(listOf(1, 1, 0, 0, 1, 1, 1, 0, 0))
        )
        assertEquals(
            listOf(4, 2, 3, 0),
            api.toVectorRepresentation(listOf(1, 1, 1, 1, 0, 0, 1, 1, 1))
        )
        assertEquals(
            listOf(2, 1, 1, 4, 2, 1, 1, 3),
            api.toVectorRepresentation(listOf(1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0))
        )
    }

    @Test
    fun toBinaryRepresentation_works() {
        val V = listOf(3, 4, 2, 4, 1, 0)
        assertEquals(
            listOf(1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1),
            api.toBinaryRepresentation(V)
        )
    }

    @Test
    fun binaryVectorRoundTrip_isConsistent() {
        val V = listOf(3, 4, 2, 4, 1, 0)
        val binary = api.toBinaryRepresentation(V)
        val V2 = api.toVectorRepresentation(binary)
        val binary2 = api.toBinaryRepresentation(V2)
        assertEquals(binary, binary2)
    }

    @Test
    fun findMinimalPeriodGeneralCase_works() {
        val V = listOf(2, 1, 1, 4, 2, 1, 1, 3)
        val p = 0
        val period = api.findMinimalPeriodGeneralCase(V, p)
        assertEquals(8L, period)
    }

    @Test
    fun validateParameters_doesNotThrow_whenConditionIsMet() {
        val V = listOf(2, 1, 1, 4, 2, 1, 1, 3)
        val p = 0
        api.validateParameters(V, p) // should not throw
    }

    @Test
    fun validateParameters_throws_whenConditionIsNotMet() {
        val V = listOf(2, 1, 1, 4, 2, 1, 1, 3)
        val p = 99
        assertThrows<IllegalArgumentException> { api.validateParameters(V, p) }
    }

    @Test
    fun validateParameters_isConsistentWithGeneralCase() {
        val V = listOf(2, 1, 1, 4, 2, 1, 1, 3)
        val p = 99
        assertThrows<IllegalArgumentException> { api.validateParameters(V, p) }
        assertThrows<IllegalArgumentException> { api.findMinimalPeriodGeneralCase(V, p) }
    }

    @Test
    fun validateParameters_withK_doesNotThrow_whenBothConditionsMet() {
        val V = listOf(2, 1, 1, 4, 2, 1, 1, 3)
        val p = 0
        val k = api.findSuitableK(V, p) // k = 7, w(A) = k + p + 1 = 8 ✓
        api.validateParameters(V, p, k) // should not throw
    }

    @Test
    fun validateParameters_withK_throws_whenWrongK() {
        val V = listOf(2, 1, 1, 4, 2, 1, 1, 3)
        val p = 0
        val k = 99 // w(A) = k + p + 1 ikke oppfylt
        assertThrows<IllegalArgumentException> { api.validateParameters(V, p, k) }
    }

    @Test
    fun validateParameters_withK_throws_whenPTooLarge() {
        val V = listOf(2, 1, 1, 4, 2, 1, 1, 3)
        val p = 99
        val k = api.findSuitableK(V, p)
        assertThrows<IllegalArgumentException> { api.validateParameters(V, p, k) }
    }

    @Test
    fun roundTrip_vectorToBinaryAndBack_isStableAcrossMultipleConversions() {
        val examples = listOf(
            listOf(2, 1, 1, 4, 2, 1, 1, 3),
            listOf(3, 4, 2, 4, 1, 0),
            listOf(2, 3, 1, 3),
            listOf(3, 1, 2, 2, 2, 8, 1, 1),
        )

        for (original in examples) {
            var V = original
            repeat(5) {
                val binary = api.toBinaryRepresentation(V)
                V = api.toVectorRepresentation(binary)
            }
            assertEquals(original, V, "Round-trip failed for $original")
        }
    }

    @Test
    fun roundTrip_binaryToVectorAndBack_isStableAcrossMultipleConversions() {
        // ...existing code...
    }

    @Test
    fun generateValidParameters_returnsValidCombination() {
        repeat(10) {
            val result = api.generateValidParameters()
            api.validateParameters(result.V, result.p, result.k) // throws if invalid
        }
    }

    @Test
    fun findValidPRange_returnsMinZero() {
        val V = listOf(2, 1, 1, 4, 2, 1, 1, 3)
        val range = api.findValidPRange(V)
        assertEquals(0, range.min)
    }

    @Test
    fun findValidPRange_returnsCorrectMax() {
        val V = listOf(2, 1, 1, 4, 2, 1, 1, 3)
        val range = api.findValidPRange(V)
        // hMax for this vector - max p should be hMax - 1, and p = hMax - 1 should be valid
        api.validateParameters(V, range.max) // should not throw
    }

    @Test
    fun findValidPRange_maxPlusOneIsInvalid() {
        val V = listOf(2, 1, 1, 4, 2, 1, 1, 3)
        val range = api.findValidPRange(V)
        assertThrows<IllegalArgumentException> { api.validateParameters(V, range.max + 1) }
    }

    @Test
    fun findValidPRange_allValuesInRangeAreValid() {
        val V = listOf(2, 3, 1, 3)
        val range = api.findValidPRange(V)
        for (p in range.min..range.max) {
            api.validateParameters(V, p) // should not throw for any p in range
        }
    }

    @Test
    fun findValidPRange_anotherVector() {
        val V = listOf(3, 4, 2, 4, 1, 0)
        val range = api.findValidPRange(V)
        assertEquals(0, range.min)
        api.validateParameters(V, range.max) // max should be valid
        assertThrows<IllegalArgumentException> { api.validateParameters(V, range.max + 1) }
    }
}
