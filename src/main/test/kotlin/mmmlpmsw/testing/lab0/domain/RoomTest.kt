package mmmlpmsw.testing.lab0.domain

import mmmlpmsw.testing.lab0.domain.State.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RoomTest {

    lateinit var room: Room

    @BeforeEach
    fun init() {
        room = Room("room")
    }

    @Test
    fun testInitState() {
        Assertions.assertEquals(null, room.describe())
        Assertions.assertEquals(NORMAL, room.state)
        Assertions.assertEquals(false, room.isChangeable)
    }

    @Test
    fun testOpenDoor() {
        room.openDoor(0)
        Assertions.assertEquals(COLD, room.state)

        room.openDoor(10)
        Assertions.assertEquals(NORMAL, room.state)

        room.openDoor(25)
        Assertions.assertEquals(HOT, room.state)
    }


}