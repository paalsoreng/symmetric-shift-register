package part8

/**
 * Validates that k < w(A) <= k + p + 1
 */
object StartVectorAssumptions {

    fun validate(V: List<Int>, k: Int, p: Int) {

        val w = Weigth.calculate(V)

        if (k >= w) {
            throw IllegalArgumentException("k < w(A) is not fulfilled: w(A) = $w, k=$k, p=$p")
        }

        if (w > (k + p + 1)) {
            throw IllegalArgumentException("w(A) <= k + p + 1 is not fulfilled: w(A) = $w, k=$k, p=$p")
        }
    }
}