package part3

object Factors {

    fun calculate(s: Int): List<Int> {

        val factors = mutableListOf<Int>()

        for (n in s downTo 1) {
            if (s % n == 0) {
                factors.add(n)
            }
        }
        return factors
    }

}