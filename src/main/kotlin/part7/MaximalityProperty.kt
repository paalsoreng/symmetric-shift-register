package part7

object MaximalityProperty {

    fun calculate(V: List<Int>): Pair<Int, Int> {

        val h = AlternatingSums.calculate(V)

        var t = 0
        var hMax = 0

        for (i in 1 until h.size) {

            if (h[i] >= hMax) {
                t = i
                hMax = h[i]
            } else if (h[i] <= 0) {
                return Pair(t, hMax)
            }
        }

        return Pair(t, hMax)
    }

}