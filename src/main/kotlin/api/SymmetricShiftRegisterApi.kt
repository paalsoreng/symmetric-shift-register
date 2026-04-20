package api

import part6.BinaryRepresentation
import part6.GeneralCase
import part6.VectorRepresentation
import part7.AdjustmentOfParameters
import part7.MaximalityProperty
import report.ShiftAndFlipReportGenerator
import simulation.Simulation
import startvector.AllCases
import testdata.RandomVector
import trace.ExecutionLog

/**
 * Holds a valid combination of vector V and parameters p and k.
 * All three are guaranteed to satisfy the required assumptions for the symmetric shift register.
 * @property V The vector
 * @property p The parameter p
 * @property k The parameter k
 */
data class ValidParametersResponse(val V: List<Int>, val p: Int, val k: Int)

/**
 * Holds the valid range of p values for a given vector V.
 * @property min The lowest valid value for p (always 0)
 * @property max The highest valid value for p (= hMax - 1, where hMax is the peak of the alternating sum sequence)
 */
data class ValidPRangeApiData(val min: Int, val max: Int)

/**
 * Holds the result of a structured report split into four sections.
 * @property mainResults The main conclusion: minimal period and least even vector period
 * @property contractionResults The step-by-step contraction of Q down to Q[0]
 * @property periodicResults The periodic parameters (zeta and j) for each contraction level
 * @property intermediateResults The detailed intermediate calculations for each step
 */
data class StructuredReportResponse(
    val mainResults: String,
    val contractionResults: String,
    val periodicResults: String,
    val intermediateResults: String
)

/**
 * Public API for the symmetric shift register library.
 *
 * Provides methods for computing minimal periods, generating and validating parameters,
 * converting between binary and vector representations, simulating the register,
 * and producing human-readable reports.
 *
 * All input vectors V are on vector representation form, and all parameters p and k
 * must satisfy the required assumptions (see [validateParameters]).
 */
class SymmetricShiftRegisterApi {

    /**
     * Builds a structured report for the given vector and parameter,
     * with the results split into four separate sections.
     * @param V The vector
     * @param p The parameter p
     * @return A [StructuredReportResponse] with fields mainResults, contractionResults, periodicResults and intermediateResults
     */
    fun buildStructuredReport(V: List<Int>, p: Int): StructuredReportResponse {
        val log = ExecutionLog()
        val report = report.buildStructuredReport(V, p, log)
        return StructuredReportResponse(
            mainResults = report.mainResults,
            contractionResults = report.contractionResults,
            periodicResults = report.periodicResults,
            intermediateResults = report.intermediateResults
        )
    }

    /**
     * Finds the minimal period for a vector V with given parameters.
     * @param V The vector
     * @param p The parameter p
     * @param k The parameter k
     * @return The minimal period
     */
    fun findMinimalPeriodByIteratingRegister(V: List<Int>, p: Int, k: Int): Int {
        validateParameters(V, p, k)
        return Simulation.findMinimalPeriod(V, p, k)
    }

    /**
     * Finds the minimal period for all cases given a vector V and parameter p.
     * @param V The vector
     * @param p The parameter p
     * @return The minimal period
     */
    fun findMinimalPeriodForAllCases(V: List<Int>, p: Int): Long {
        val binary = BinaryRepresentation.calculate(V)
        return AllCases.findMinimalPeriod(binary, p)
    }

    /**
     * Finds the minimal period for a given vector and parameter p using the general case algorithm.
     * @param V The vector
     * @param p The parameter p
     * @return The minimal period
     */
    fun findMinimalPeriodGeneralCase(V: List<Int>, p: Int): Long {
        validateParameters(V, p)
        return GeneralCase.findMinimalPeriod(V, p)
    }

    /**
     * Validates that the parameters V and p satisfy the required assumption p + 1 <= hMax.
     * @param V The vector
     * @param p The parameter p
     * @throws IllegalArgumentException if p (=$p) is not less than the peak of the alternating sum sequence.
     */
    fun validateParameters(V: List<Int>, p: Int) {
        val (_, hMax) = MaximalityProperty.calculate(V)
        if (p + 1 > hMax)
            throw IllegalArgumentException(
                "Invalid parameter: p=$p for V=$V. " +
                    "p (=$p) must be less than the peak of the alternating sum sequence (= $hMax), so valid values for p are 0 to ${hMax - 1}."
            )
    }

    /**
     * Validates that the parameters V, p and k satisfy both required assumptions:
     * - w(A) = k + p + 1  (number of one-bits equals k + p + 1)
     * - p + 1 <= hMax
     * @param V The vector
     * @param p The parameter p
     * @param k The parameter k
     * @throws IllegalArgumentException if either condition is not met.
     */
    fun validateParameters(V: List<Int>, p: Int, k: Int) {
        val binary = BinaryRepresentation.calculate(V)
        val numberOfOneBits = binary.sum()
        val (_, hMax) = MaximalityProperty.calculate(V)
        if (numberOfOneBits != k + p + 1)
            throw IllegalArgumentException(
                "Invalid parameters: p=$p, k=$k for V=$V. " +
                    "Requirement 1: the number of one-bits in the binary representation ($numberOfOneBits) must equal k + p + 1 (= ${k + p + 1}). " +
                    "Requirement 2: p (=$p) must be less than the peak of the alternating sum sequence (= $hMax), so valid values for p are 0 to ${hMax - 1}."
            )
        if (p + 1 > hMax)
            throw IllegalArgumentException(
                "Invalid parameters: p=$p, k=$k for V=$V. " +
                    "p (=$p) must be less than the peak of the alternating sum sequence (= $hMax), so valid values for p are 0 to ${hMax - 1}."
            )
    }

    /**
     * Returns the valid range of p values for a given vector V.
     *
     * Valid values for p are 0 to hMax - 1, where hMax is the peak of the alternating sum sequence.
     * This corresponds to the constraint p + 1 <= hMax required by the symmetric shift register.
     *
     * @param V The vector
     * @return A [ValidPRangeApiData] with the lowest (min) and highest (max) valid p value.
     * @throws IllegalArgumentException if no valid p exists for the given vector.
     */
    fun findValidPRange(V: List<Int>): ValidPRangeApiData {
        val (_, hMax) = MaximalityProperty.calculate(V)
        if (hMax == 0)
            throw IllegalArgumentException("No valid p exists for V=$V: the alternating sum sequence has no positive peak.")
        return ValidPRangeApiData(min = 0, max = hMax - 1)
    }

    /**
     * Finds a suitable k for a given vector V and parameter p.
     * The method uses the same logic as in AdjustmentOfParameters: k = numberOfOneBits - (p + 1)
     * @param V The vector
     * @param p The parameter p
     * @return A suitable k value
     */
    fun findSuitableK(V: List<Int>, p: Int): Int {
        val binary = BinaryRepresentation.calculate(V)
        val numberOfOneBits = binary.sum()
        return numberOfOneBits - (p + 1)
    }

    /**
     * Converts a binary list to its vector representation.
     * @param binary The binary register as a list of Ints (0/1)
     * @return The vector representation
     */
    fun toVectorRepresentation(binary: List<Int>): List<Int> {
        return VectorRepresentation.calculate(binary)
    }

    /**
     * Converts a vector representation to its binary register.
     * @param V The vector
     * @return The binary register as a list of Ints (0/1)
     */
    fun toBinaryRepresentation(V: List<Int>): List<Int> {
        return BinaryRepresentation.calculate(V)
    }

    /**
     * Simulates the shift register step by step and returns a human-readable explanation
     * of each step, showing whether the leftmost bit is flipped or passed through and why.
     * The binary representation of V is used as the register start state.
     *
     * @param V The vector
     * @param p The parameter p
     * @param k The parameter k
     * @return The full simulation trace as a String.
     */
    fun simulateWithExplanation(V: List<Int>, p: Int, k: Int): String {
        val period = findMinimalPeriodGeneralCase(V, p)
        if (period > 1000) {
            return "Cannot simulate: period is $period which exceeds the maximum of 1000."
        }
        val binary = BinaryRepresentation.calculate(V)
        return ShiftAndFlipReportGenerator(binary, k, p).simulateWithExplanation()
    }

    /**
     * Generates a random vector V together with a valid p and k.
     *
     * The vector is drawn at random, and p is chosen randomly among all valid values
     * (0 until hMax). k is then derived from p using k = w(A) - (p + 1),
     * where w(A) is the number of one-bits in the binary representation of V.
     *
     * @param maxLength Maximum length of the generated vector. Defaults to 8.
     * @param maxValue Maximum element value in the generated vector. Defaults to 5.
     * @return A [ValidParametersResponse] instance with a random but valid V, p and k.
     */
    fun generateValidParameters(maxLength: Int = 8, maxValue: Int = 5): ValidParametersResponse {
        while (true) {
            val V = RandomVector.generateRandomVectorList(1, maxLength, maxValue).firstOrNull()
                ?: continue
            val periods = AdjustmentOfParameters.findAllMinimalPeriods(V)
            if (periods.isEmpty()) continue
            val chosen = periods.random()
            return ValidParametersResponse(V, chosen.p, chosen.k)
        }
    }
}
