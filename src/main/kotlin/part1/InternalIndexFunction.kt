package part1

object InternalIndexFunction {

    fun calculate(v: List<Int>, rStart: Int): Int {

        val s = v.size
        var r = rStart
        var t = 0

        while ((r + 2) < s) {
            if (v[r + 1] == 1) {  // index 0 in Kotlin
                t = t + 1
                r = r + 2
            } else {
                return t
            }
        }

        return t
    }
}