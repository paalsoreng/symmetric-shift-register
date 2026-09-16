package simulation.fastalgorithm

import part6.BinaryRepresentation

fun main() {

    val qin = readvector()
    println("qin =$qin")
    val ain = BinaryRepresentation.calculate(qin)
    val asum = ain.sum()

    println()
    print("p: ")
    val p = readln().toInt()

    println()
    val n = ain.size
    val k = readkvalue(n, p, asum)
    println()

    val period = findMinimalPeriod(ain, p, k)
    if (period != null) {
        println("Minimal period: $period")
    } else {
        println("No period found")
    }
}

fun readvector(): List<Int> {
    print("Q: ")
    val ss = readln().trim()
    val ssok = ss.split(",")
    val v = ssok.map { it.trim().toInt() }
    return v
}

fun readkvalue(n: Int, p: Int, asum: Int): Int {
    val (kmin, kmax) = calculateKBounds(n, p, asum)
    println("kmin = $kmin")
    println("kmax = $kmax")
    println()

    print("k = ")
    return readln().toInt()
}
