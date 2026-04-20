package verification

import part7.AdjustmentOfParameters
import part7.PeriodResponse
import simulation.Simulation

data class VerificationCase(
    val V: List<Int>,
    val p: Int,
    val k: Int,
    val analytical: Long,
    val simulated: Long,
    val match: Boolean
)

data class VerificationResult(
    val cases: List<VerificationCase>,
    val skipped: Int
) {
    val total get() = cases.size
    val allMatch get() = cases.all { it.match }
    val mismatches get() = cases.filter { !it.match }
}

/**
 * Core exhaustive verification logic – shared between the runner app and the JUnit test.
 */
object ExhaustiveVerification {

    /**
     * Generates all vectors [v1, v2, ..., vn] where:
     * - length is between minLen and maxLen
     * - each element is between 1 and maxVal
     */
    fun allVectors(minLen: Int, maxLen: Int, maxVal: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        fun generate(current: List<Int>, targetLen: Int) {
            if (current.size == targetLen) {
                result.add(current)
                return
            }
            for (v in 1..maxVal) generate(current + v, targetLen)
        }
        for (len in minLen..maxLen) generate(emptyList(), len)
        return result
    }

    /**
     * Runs exhaustive verification over all vectors in the given range.
     * For each vector and each valid p, compares analytical vs simulated period.
     *
     * @param onProgress called for each completed case (for live logging)
     * @param onSkip     called for each vector with no valid p
     */
    fun verify(
        minLen: Int,
        maxLen: Int,
        maxVal: Int,
        onProgress: (VerificationCase) -> Unit = {},
        onSkip: (List<Int>) -> Unit = {}
    ): VerificationResult {
        val cases = mutableListOf<VerificationCase>()
        var skipped = 0

        for (V in allVectors(minLen, maxLen, maxVal)) {
            val periodResponses: List<PeriodResponse> = try {
                AdjustmentOfParameters.findAllMinimalPeriods(V)
            } catch (_: Exception) {
                onSkip(V)
                skipped++
                continue
            }

            for (pr in periodResponses) {
                val simulated = Simulation.findMinimalPeriod(V, pr.p, pr.k)
                val case = VerificationCase(
                    V          = V,
                    p          = pr.p,
                    k          = pr.k,
                    analytical = pr.minimalPeriod,
                    simulated  = simulated.toLong(),
                    match      = pr.minimalPeriod == simulated.toLong()
                )
                cases.add(case)
                onProgress(case)
            }
        }

        return VerificationResult(cases, skipped)
    }
}

