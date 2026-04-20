package part2

object CyclicParameters {

    fun calculate(v: List<Int>): CyclicParametersResponse {
        val s = v.size
        val evenFactors = EvenFactors.calculate(s)

        for (e in evenFactors) {
            val shifted = ShiftMap.calculate(v, e)
            if (shifted == v) {
                return CyclicParametersResponse(
                    j = e,
                    B = v.subList(0, e).sum().toLong()
                )
            }
        }
        throw IllegalStateException("Did not found an answer. Should not happen.")
    }

    data class CyclicParametersResponse(
        val j: Int,
        val B: Long
    )
}