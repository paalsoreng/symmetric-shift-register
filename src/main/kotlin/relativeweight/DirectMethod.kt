package relativeweight

import part6.BinaryRepresentation


object DirectMethod {

    fun findPRange(q: List<Int>): Pair<Int, Int> {
        val binary = BinaryRepresentation.calculate(q)
        val n = binary.size
        val pMax = n - 1
        return Pair(0, pMax)
    }

    fun findKRange(q: List<Int>, p: Int): Pair<Int, Int> {
        val binary = BinaryRepresentation.calculate(q)
        val n = binary.size
        val sum = binary.sum()
        val kMin = maxOf(0, sum - (p + 1))
        val kMax = minOf(sum, n - (p + 1))
        return Pair(kMin, kMax)
    }

    fun findPeriod(q: List<Int>, p: Int, k: Int, maxCount: Int = 1000000): Int {
        val aOriginal = BinaryRepresentation.calculate(q)
        var a = aOriginal.toMutableList()
        val wa = a.sum()
        var w = wa - k
        var counter = 0

        while (true) {
            val (aNew, wNew) = aux(a[0], w, p)
            a = shift(a, aNew)
            w = wNew
            counter++
            if (counter > maxCount) {
                println("Exceeded maximum count: $maxCount")
                break
            }
            if (a == aOriginal) {
                println("Minimal period: $counter")
                break
            }
        }
        return counter
    }


    private fun aux(a: Int, w: Int, p: Int): Pair<Int, Int> {
        var aNew: Int
        var wNew: Int
        when {
            a == 0 && w == p + 1 -> {
                wNew = p + 1
                aNew = 0
            }

            a == 0 && w < p + 1 && w >= 0 -> {
                wNew = w + 1
                aNew = 1
            }

            a == 1 && w == 0 -> {
                wNew = 0
                aNew = 1
            }

            a == 1 && w <= p + 1 && w > 0 -> {
                wNew = w - 1
                aNew = 0
            }

            else -> {
                wNew = 10001
                aNew = 1000
            }
        }

        return Pair(aNew, wNew)
    }

    fun shift(A: List<Int>, aNew: Int): MutableList<Int> {
        val B = A.toMutableList()
        B.add(aNew)
        B.removeAt(0)
        return B
    }
}

