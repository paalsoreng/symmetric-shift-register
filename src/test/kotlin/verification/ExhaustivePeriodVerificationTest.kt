package verification

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * JUnit wrapper around ExhaustiveVerification.
 * The core logic lives in ExhaustiveVerification (src/main/kotlin) and is shared
 * with ExhaustivePeriodVerificationRunner (the standalone app).
 *
 * Set verbose = true to print each case while the test runs.
 */
class ExhaustivePeriodVerificationTest {

    private val verbose = true

    private fun log(msg: String) { if (verbose) println(msg) }

    @Test
    fun `all vectors up to length 4 with values up to 4 - analytical equals simulated for all valid p`() {
        log("=== Exhaustive verification: length 2–4, values 1–4 ===")

        val result = ExhaustiveVerification.verify(
            minLen = 2,
            maxLen = 4,
            maxVal = 4,
            onProgress = { case ->
                val status = if (case.match) "OK   " else "FAIL "
                log("  $status V=${case.V}  p=${case.p}  k=${case.k}  analytical=${case.analytical}  simulated=${case.simulated}")
            },
            onSkip = { V ->
                log("  SKIP  V=$V  (no valid p)")
            }
        )

        log("=== Done: ${result.total} cases verified, ${result.skipped} vectors skipped ===")

        if (!result.allMatch) {
            for (m in result.mismatches) {
                println("  MISMATCH: V=${m.V}  p=${m.p}  k=${m.k}  analytical=${m.analytical}  simulated=${m.simulated}")
            }
        }

        assertTrue(result.allMatch, "Expected all periods to match, but got ${result.mismatches.size} mismatches")
        assertTrue(result.total > 0, "Expected at least one case to be verified")
    }

    @Test
    fun `all vectors of length 2 to 3 with values up to 5 - simulated period confirms analytical`() {
        log("=== Minimality check: length 2–3, values 1–5 ===")

        val result = ExhaustiveVerification.verify(
            minLen = 2,
            maxLen = 3,
            maxVal = 5,
            onProgress = { case ->
                log("  OK    V=${case.V}  p=${case.p}  k=${case.k}  period=${case.analytical}")
            },
            onSkip = { V ->
                log("  SKIP  V=$V  (no valid p)")
            }
        )

        log("=== Done: ${result.total} cases verified, ${result.skipped} vectors skipped ===")

        for (case in result.cases) {
            assertEquals(
                case.analytical, case.simulated,
                "Period mismatch for V=${case.V} p=${case.p} k=${case.k}: analytical=${case.analytical}, simulated=${case.simulated}"
            )
        }
    }
}
