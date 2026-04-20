package part3

object ProgressionParameters {

    fun calculate(D: List<Int>, α: Int): ProgressionParametersResponse {
        val y = D.size
        val f = m(y, α)
        val j = f.size
        val E = e(D, α)

        for (i in 1..j) {
            val B = α / f[i - 1]
            val r = AuxiliaryFunction.calculate(D, B)
            val set1 = E.subList(fromIndex = r , toIndex = r + y)
            val set2 = dPlus(D, B)

            if (set1 == set2) {
                return ProgressionParametersResponse(B, r)
            }
        }
        throw IllegalStateException("Comparison failed")
    }

    fun e(D: List<Int>, α: Int): List<Int> {
        val Dplus = dPlus(D, α)
        return D + Dplus
    }

    fun dPlus(D: List<Int>, α: Int) = D.map { it + α }

    fun m(a: Int, b: Int): List<Int> {
        val gcd = GreatestCommonDivisor.calculate(a, b)
        return factors(gcd)
    }

    fun factors(n: Int): List<Int> {
        val factorsList = mutableListOf<Int>()
        for (i in 1..n) {
            if (n % i == 0) {
                factorsList.add(i)
            }
        }
        return factorsList.sortedDescending()
    }
}

