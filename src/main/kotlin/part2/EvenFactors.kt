package part2

object EvenFactors {

    fun calculate(s: Int): MutableList<Int> {
        val evenFactors = mutableListOf<Int>()

        var i = 2

        while (i <= s) {
            val modulo = s % i
            if (modulo == 0) {
                evenFactors.add(i)
            }
            i = i + 2
        }

        return evenFactors
    }

}