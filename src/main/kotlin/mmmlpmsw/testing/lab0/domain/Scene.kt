package mmmlpmsw.testing.lab0.domain

interface Scene {
    val name: String
    var isChangeable: Boolean
    fun describe(): String?
}