package mmmlpmsw.testing.lab0.domain

class Human(private val name: String, var scene: Scene) {
    fun count(computer: Computer, comparison: String?): String {
        computer.afraid()
        return "$name считает вслух. $comparison"
    }
    fun said(computer: Computer, phrase: String): String {
        computer.calm()
        return "$name тихо сказал: '$phrase'."
    }
    fun changeScene(oldScene: Scene, newScene: Scene): String {
        if (oldScene == newScene)
            throw ChangeSceneException()
        return if (oldScene.isChangeable) {
            scene = newScene
            "$name сменил локацию на ${newScene.name} - это ${newScene.describe()}."
        } else "$name не может сменить локацию на ${newScene.name}."

    }

}