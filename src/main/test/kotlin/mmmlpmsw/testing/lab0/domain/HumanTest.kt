package mmmlpmsw.testing.lab0.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class HumanTest {
    lateinit var human: Human
    lateinit var sceneMock: Scene

    @BeforeEach
    fun init() {
        sceneMock = mock(Scene::class.java)
        human = Human("human", sceneMock)
    }

    @Test
    fun testCount() {
        Assertions.assertEquals("human считает вслух. ", human.count(mock(Computer::class.java), "") )
    }

    @Test
    fun testSaid() {
        Assertions.assertEquals("human тихо сказал: 'aaaaa'.", human.said(mock(Computer::class.java), "aaaaa") )
    }

    @Test
    fun testChangeScene() {
        Mockito.`when`(sceneMock.isChangeable).thenReturn(true)

        Assertions.assertThrows(ChangeSceneException::class.java) {human.changeScene(sceneMock, sceneMock)}

        val newScene = object : Scene {
            override val name = ""
            override var isChangeable = false
            override fun describe() = ""
        }

        human.changeScene(sceneMock, newScene)
        Assertions.assertEquals(newScene, human.scene)
    }

}