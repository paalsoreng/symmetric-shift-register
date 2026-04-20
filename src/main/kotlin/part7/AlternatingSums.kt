package part7

object AlternatingSums {

    fun calculate(V: List<Int>): List<Int> {

        val h = mutableListOf<Int>()
        h.add(0)

        var alt = 1

        for (i in V.indices) {
            h += h[i] + V[i] * alt
            alt *= -1
        }

        return h
    }

}