package mmmlpmsw.testing.lab0.domain

open class Room(override val name: String) : Scene {
    var state: State = State.NORMAL
    override var isChangeable: Boolean = false
        get() = field
        set(value) {field = value}

    fun openDoor(outsideTemperature: Int): String {
        state = when {
            outsideTemperature <= 0 -> State.COLD
            outsideTemperature >= 25 -> State.HOT
            else -> State.NORMAL
        }

        return "Дверь открылась и ${state.s}."
    }

    override fun describe(): String? = null

}