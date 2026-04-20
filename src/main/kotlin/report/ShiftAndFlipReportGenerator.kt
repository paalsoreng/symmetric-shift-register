package report

/**
 * Simulates a symmetric shift register with parameters k and p.
 *
 * The register works by shifting the binary list one step at a time.
 * At each step, the leftmost bit is either fed back unchanged or flipped,
 * depending on whether the sum of the remaining bits falls within the
 * range [k, k+p].
 *
 * @param startA The initial state of the register as a binary list (0s and 1s).
 * @param k The lower threshold for the feedback rule.
 * @param p The width of the threshold window; feedback is active when sum is in [k, k+p].
 */
class ShiftAndFlipReportGenerator(
    private val startA: List<Int>,
    private val k: Int,
    private val p: Int
) {

    /**
     * Simulates the register step by step and returns an explanation for each step as a String,
     * showing whether the leftmost bit is flipped or passed through and why.
     * Stops when the register returns to its initial state, then appends the period.
     *
     * @return The full simulation trace as a String.
     */
    fun simulateWithExplanation(): String {
        var current = startA
        var count = 0L
        val sb = StringBuilder()

        sb.appendLine("Register length: ${startA.size}, k=$k, p=$p -> flip range: [$k, ${k + p}] (lower bound is k, upper bound is k+p=$k+$p=${k + p})")
        sb.appendLine("At each step the register shifts one position to the left: the leftmost bit is removed and a new bit is appended on the right (either flipped or unchanged).")
        sb.appendLine()

        do {
            val sum = current.drop(1).sum()
            val flipped = sum in k..k + p
            val explanation = if (flipped)
                "-> leftmost bit [${current.first()}] is flipped (sum of rest=$sum is in [$k, ${k + p}])"
            else
                "-> leftmost bit [${current.first()}] is passed through (sum of rest=$sum is outside [$k, ${k + p}])"

            sb.appendLine("$count: $current $explanation")
            current = nextBinary(current)
            count++
        } while (current != startA)

        sb.appendLine()
        sb.appendLine("Completed after $count steps: register returned to its initial state $startA -> period = $count")

        return sb.toString()
    }

    /**
     * Computes the next state of the register by shifting all bits one position to the left.
     *
     * The new rightmost bit is determined by the feedback rule:
     * - If the sum of bits a[1..n-1] is in the range [k, k+p], the leftmost bit is flipped.
     * - Otherwise, the leftmost bit is passed through unchanged.
     *
     * @param a The current register state as a binary list.
     * @return The next register state.
     */
    private fun nextBinary(a: List<Int>): List<Int> {
        val sum = a.drop(1).sum()

        if (k <= sum && sum <= k + p) {
            val nySiste = 1 - a.first()
            return a.drop(1) + nySiste
        } else {
            return a.drop(1) + a.first()
        }
    }
}