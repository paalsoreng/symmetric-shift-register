package part8

object Weigth {

    fun calculate(V: List<Int>): Int {
        var numberOfOneBits = 0

        for (i in V.indices) {
            if (isEven(i)) {
                numberOfOneBits += V[i]
            }
        }
        return numberOfOneBits
    }

    private fun isEven(value: Int) = value % 2 == 0
}