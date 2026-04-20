package part7

import part6.BinaryRepresentation
import part6.GeneralCase

object AdjustmentOfParameters {

    fun findAllMinimalPeriods(V: List<Int>): List<PeriodResponse> {

        val binary = BinaryRepresentation.calculate(V)
        val numberOfOneBits = binary.sum()
        val (_, hMax) = MaximalityProperty.calculate(V)

        val pValues = 0 until hMax

        val responseList = mutableListOf<PeriodResponse>()

        for (p in pValues) {
            val k = numberOfOneBits - (p + 1)
            val minimalPeriod = GeneralCase.findMinimalPeriod(V, p)
            responseList.add(PeriodResponse(k, p, minimalPeriod))
        }
        return responseList
    }
}

data class PeriodResponse(
    val k: Int,
    val p: Int,
    val minimalPeriod: Long
)