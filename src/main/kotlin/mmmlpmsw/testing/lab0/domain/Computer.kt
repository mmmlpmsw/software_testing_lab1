package mmmlpmsw.testing.lab0.domain

class Computer {
    fun tryOpenDoor(room: Room, temperature: Int = 1): String {
        return when (state) {
            ComputerState.CALM ->  {
                room.isChangeable = true
                room.openDoor(temperature)
            }
            ComputerState.SCARED -> "Компьютер напуган. Не удалось открыть дверь."
            else -> ""
        }
    }

    fun afraid() {
        state = ComputerState.SCARED
    }

    fun calm() {
        state = ComputerState.CALM
    }

    var state: ComputerState = ComputerState.INIT
        private set
}

sealed class ComputerState {
    object INIT: ComputerState()
    object CALM: ComputerState()
    object SCARED: ComputerState()
}