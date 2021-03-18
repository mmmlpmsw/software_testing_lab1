package mmmlpmsw.testing.lab0.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StateTest {
    @Test
    fun checkStateDescription() {
        Assertions.assertEquals("стало жарко.", State.HOT.s)
        Assertions.assertEquals("ничего не изменилось.", State.NORMAL.s)
        Assertions.assertEquals("подул холодный ветер.", State.COLD.s)
    }
}