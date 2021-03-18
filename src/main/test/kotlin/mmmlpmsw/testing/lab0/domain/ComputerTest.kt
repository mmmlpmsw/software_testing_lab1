package mmmlpmsw.testing.lab0.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

class ComputerTest {
    lateinit var computer: Computer

    @BeforeEach
    fun init() {
        computer = Computer()
    }

    @Test
    fun testState() {
        Assertions.assertEquals(ComputerState.INIT, computer.state)

        computer.afraid()
        Assertions.assertEquals(ComputerState.SCARED, computer.state)

        computer.calm()
        Assertions.assertEquals(ComputerState.CALM, computer.state)
    }

    @Test
    fun testOpenDoor() {
        val room = mock(Room::class.java)
        Assertions.assertEquals("", computer.tryOpenDoor(room))

        computer.afraid()
        Assertions.assertEquals("Компьютер напуган. Не удалось открыть дверь.", computer.tryOpenDoor(room))

        computer.calm()
        Assertions.assertEquals("Дверь открылась и ничего не изменилось..", computer.tryOpenDoor(room))

    }
}