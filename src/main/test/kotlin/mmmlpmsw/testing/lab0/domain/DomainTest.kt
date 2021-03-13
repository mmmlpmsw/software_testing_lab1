package mmmlpmsw.testing.lab0.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.lang.NumberFormatException
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DomainTest {

    val correctState =
    "Форд считает вслух. Это одно из самых агрессивных действий, которые вы можете применить к компьютеру, равносильное тому, чтобы медленно приближаться в темноте к человеку, повторяя: \"Умри... умри... умри...\"." +
    "Эдди тихо сказал: 'Я вижу, нам придется поработать над нашими отношениями'." +
    "Дверь открылась и подул холодный ветер.." +
    "Эдди сменил локацию на Магратея - это безжизненная планета.Форд сменил локацию на Магратея - это безжизненная планета."

    @Test
    fun testCorrectInitialStates() {
        val room = Room("помещение")
        Assertions.assertEquals(State.NORMAL, room.state)
        Assertions.assertEquals(false, room.isChangeable)

        val ford = Human("Форд", room)
        Assertions.assertEquals(room, ford.scene)

        val eddy = Human("Эдди", room)
        Assertions.assertEquals(room, eddy.scene)

        val computer = Computer()
        Assertions.assertEquals(ComputerState.INIT, computer.state)

        val planet = Magrateya
        Assertions.assertEquals(true, planet.isChangeable)
        Assertions.assertEquals("Магратея", planet.name)
    }

    @Test
    fun testCorrectDomain() {
        val room = Room("помещение")
        val ford = Human("Форд", room)
        val eddy = Human("Эдди", room)
        val computer = Computer()
        val planet = Magrateya

        val s1 = ford.count(computer, "Это одно из самых агрессивных действий, которые вы можете применить к компьютеру, равносильное тому, чтобы медленно приближаться в темноте к человеку, повторяя: \"Умри... умри... умри...\".")
        Assertions.assertEquals(ComputerState.SCARED, computer.state)
        Assertions.assertEquals(false, room.isChangeable)

        val s2 = eddy.said(computer, "Я вижу, нам придется поработать над нашими отношениями")
        Assertions.assertEquals(ComputerState.CALM, computer.state)
        Assertions.assertEquals(State.NORMAL, room.state)

        val s3 = computer.tryOpenDoor(room, -30)
        Assertions.assertEquals(true, room.isChangeable)
        Assertions.assertEquals(State.COLD, room.state)

        val s4 = eddy.changeScene(room, planet)
        val s5 = ford.changeScene(room, planet)
        Assertions.assertEquals(planet, eddy.scene)
        Assertions.assertEquals(planet, ford.scene)

        Assertions.assertEquals(correctState, s1 + s2 + s3 + s4 + s5)
    }

    @Test
    fun testWithoutCount() {
        val room = Room("помещение")
        val ford = Human("Форд", room)
        val eddy = Human("Эдди", room)
        val computer = Computer()
        val planet = Magrateya

        Assertions.assertNotEquals(ComputerState.SCARED, computer.state)
        Assertions.assertEquals(false, room.isChangeable)

        val s2 = eddy.said(computer, "Я вижу, нам придется поработать над нашими отношениями")
        Assertions.assertEquals(ComputerState.CALM, computer.state)
        Assertions.assertEquals(State.NORMAL, room.state)

        val s3 = computer.tryOpenDoor(room, -30)
        Assertions.assertEquals(true, room.isChangeable)
        Assertions.assertEquals(State.COLD, room.state)

        val s4 = eddy.changeScene(room, planet)
        val s5 = ford.changeScene(room, planet)
        Assertions.assertEquals(planet, eddy.scene)
        Assertions.assertEquals(planet, ford.scene)

        Assertions.assertNotEquals(correctState, s2 + s3 + s4 + s5)

    }

    @Test
    fun testWithoutSaying() {
        val room = Room("помещение")
        val ford = Human("Форд", room)
        val eddy = Human("Эдди", room)
        val computer = Computer()
        val planet = Magrateya

        val s1 = ford.count(computer, "Это одно из самых агрессивных действий, которые вы можете применить к компьютеру, равносильное тому, чтобы медленно приближаться в темноте к человеку, повторяя: \"Умри... умри... умри...\".")
        Assertions.assertEquals(ComputerState.SCARED, computer.state)
        Assertions.assertEquals(false, room.isChangeable)

        Assertions.assertNotEquals(ComputerState.CALM, computer.state)
        Assertions.assertEquals(State.NORMAL, room.state)

        val s3 = computer.tryOpenDoor(room, -30)
        Assertions.assertEquals("Компьютер напуган. Не удалось открыть дверь.", s3)
        Assertions.assertNotEquals(true, room.isChangeable)
        Assertions.assertNotEquals(State.COLD, room.state)

        val s4 = eddy.changeScene(room, planet)
        val s5 = ford.changeScene(room, planet)

        Assertions.assertEquals("Эдди не может сменить локацию на Магратея.", s4)
        Assertions.assertEquals("Форд не может сменить локацию на Магратея.", s5)

        Assertions.assertNotEquals(planet, eddy.scene)
        Assertions.assertNotEquals(planet, ford.scene)

        Assertions.assertEquals(room, eddy.scene)
        Assertions.assertEquals(room, ford.scene)

        Assertions.assertNotEquals(correctState, s1 + s3 + s4 + s5)
    }

    @Test
    fun testOpenDoorWithHotWeatherOutside() {
        val room = Room("помещение")
        val ford = Human("Форд", room)
        val eddy = Human("Эдди", room)
        val computer = Computer()
        val planet = Magrateya

        val s1 = ford.count(computer, "Это одно из самых агрессивных действий, которые вы можете применить к компьютеру, равносильное тому, чтобы медленно приближаться в темноте к человеку, повторяя: \"Умри... умри... умри...\".")
        Assertions.assertEquals(ComputerState.SCARED, computer.state)
        Assertions.assertEquals(false, room.isChangeable)

        val s2 = eddy.said(computer, "Я вижу, нам придется поработать над нашими отношениями")
        Assertions.assertEquals(ComputerState.CALM, computer.state)
        Assertions.assertEquals(State.NORMAL, room.state)

        val s3 = computer.tryOpenDoor(room, 30)
        Assertions.assertEquals(true, room.isChangeable)
        Assertions.assertEquals(State.HOT, room.state)
        Assertions.assertEquals("Дверь открылась и стало жарко..", s3)

        val s4 = eddy.changeScene(room, planet)
        val s5 = ford.changeScene(room, planet)
        Assertions.assertEquals(planet, eddy.scene)
        Assertions.assertEquals(planet, ford.scene)

        Assertions.assertNotEquals(correctState, s1 + s2 + s3 + s4 + s5)
    }

    @Test
    fun testIncorrectTemperature() {
        val room = Room("")
        val computer = Computer()
        Assertions.assertThrows(NumberFormatException::class.java) { computer.tryOpenDoor(room, "памагити".toInt()) }
    }

    @Test
    fun testNormalTemperature() {
        val room = Room("")
        room.openDoor(2)
        assertEquals(State.NORMAL, room.state)
    }

    @Test
    fun testNullRoomDescription() {
        val room = Room("")
        assertNull(room.describe())
    }

    @Test
    fun testIncorrectChangeScene() {
        val room = Room("помещение")
        val eddy = Human("Эдди", room)
        Assertions.assertThrows(ChangeSceneException::class.java) { eddy.changeScene(room, room) }
    }
}