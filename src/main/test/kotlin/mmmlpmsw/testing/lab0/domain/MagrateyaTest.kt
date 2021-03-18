package mmmlpmsw.testing.lab0.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MagrateyaTest {

    lateinit var magrateya: Magrateya

    @BeforeEach
    fun init() {
        magrateya = Magrateya
    }


    @Test
    fun testValues() {
        Assertions.assertEquals("Магратея", magrateya.name)
        Assertions.assertEquals("безжизненная планета", magrateya.describe())
        Assertions.assertEquals(true, magrateya.isChangeable)
    }
}