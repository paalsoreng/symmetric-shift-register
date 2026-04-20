package part4

object IndexFunction {

    fun calculate(V: List<Int>, c: Int): Int {
        val s = V.size

        if (c + 2 >= s) return 0

        for (i in (c + 2) until s) {
            if (V[i - 1] == 1) return i
        }
        return 0
    }

}