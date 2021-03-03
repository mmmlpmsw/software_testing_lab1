package mmmlpmsw.testing.lab0

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.NumberFormatException
import kotlin.math.cos

class FunctionTest {
    private val f = Function()

    @Test
    fun testLocalExtremum() {
        Assertions.assertEquals(-1.0, f.sec(Math.PI))
        Assertions.assertEquals(-1.0, f.sec(-Math.PI))
        Assertions.assertEquals(1.0, f.sec(0.0))
        Assertions.assertEquals(1.0, f.sec(2 * Math.PI))
    }

    @Test
    fun testAsymptotes() {
        Assertions.assertEquals(Double.POSITIVE_INFINITY, f.sec(Math.PI/2))
        Assertions.assertEquals(Double.POSITIVE_INFINITY, f.sec(-Math.PI/2))
    }

    @Test
    fun testYIntercept() {
        Assertions.assertEquals(1 / cos(0.0), f.sec(0.0))
    }

    @Test
    fun testLeftPartOfTheSegment() {
        Assertions.assertEquals(1 / cos(-Math.PI / 4), f.sec(Math.PI / 4), 0.001)
    }

    @Test
    fun testRightPartOfTheSegment() {
        Assertions.assertEquals(1 / cos(Math.PI / 3), f.sec(Math.PI / 3), 0.001)
    }

    @Test
    fun testInvalidArguments() {
        Assertions.assertThrows(NumberFormatException::class.java) { f.sec("памагити".toDouble()) }
        Assertions.assertEquals(Double.NaN, f.sec(Double.NaN))
    }


}