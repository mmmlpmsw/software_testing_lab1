package mmmlpmsw.testing.lab0.domain

sealed class State(val s: String) {
    object HOT: State("стало жарко.")
    object NORMAL: State("ничего не изменилось.")
    object COLD: State("подул холодный ветер.")
}