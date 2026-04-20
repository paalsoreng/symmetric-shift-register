package part3

object TestFunction {

    fun calculate(D: List<Int>, B: Int, r: Int): Int {

        val s = D.size
        val iRange = 1..(s - r)

        for (i in iRange) {
            if (D[r + i - 1] != D[i - 1] + B) {
                return 0
            }
        }
        return 1
    }

}