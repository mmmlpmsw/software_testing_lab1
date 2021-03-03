package mmmlpmsw.testing.lab0

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.NumberFormatException
import kotlin.math.cos

class SecCosFunctionTest {

    private val f = SecCosFunction()

    @Test
    fun testAsymptotes(){
        Assertions.assertEquals(Double.POSITIVE_INFINITY, f.sec(Math.PI/2))
        Assertions.assertEquals(Double.POSITIVE_INFINITY, f.sec(-Math.PI/2))
    }

    @Test
    fun testPositiveLocalMinimum() {
        Assertions.assertEquals(1 / cos(0.0), f.sec(0.0), 1e-8)
        Assertions.assertEquals(1 / cos(2 * Math.PI), f.sec(2 * Math.PI), 1e-8)
    }

    @Test
    fun testYNegativeLocalMaximum() {
        Assertions.assertEquals(1 / cos(Math.PI), f.sec(Math.PI), 1e-8)
        Assertions.assertEquals(1 / cos(3 * Math.PI), f.sec(3 * Math.PI), 1e-8)
    }

    @Test
    fun testLeftPartOfTheSegment() {
        Assertions.assertEquals(1 / cos(-Math.PI / 4), f.sec(Math.PI / 4), 1e-8)
    }

    @Test
    fun testRightPartOfTheSegment() {
        Assertions.assertEquals(1 / cos(Math.PI / 3), f.sec(Math.PI / 3), 1e-8)
    }

    @Test
    fun testInvalidArguments() {
        Assertions.assertThrows(NumberFormatException::class.java) { f.sec("памагити".toDouble()) }
        Assertions.assertEquals(Double.NaN, f.sec(Double.NaN))
    }
}