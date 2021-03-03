package mmmlpmsw.testing.lab0

import mmmlpmsw.testing.lab0.domain.Computer
import mmmlpmsw.testing.lab0.domain.Human
import mmmlpmsw.testing.lab0.domain.Magrateya
import mmmlpmsw.testing.lab0.domain.Room


fun main() {
    val room = Room("помещение")
    val ford = Human("Форд", room)
    val eddy = Human("Эдди", room)
    val computer = Computer()
    val planet = Magrateya

    ford.count(computer, "Это одно из самых агрессивных действий, которые вы можете применить к компьютеру, равносильное тому, чтобы медленно приближаться в темноте к человеку, повторяя: \"Умри... умри... умри...\".")
    eddy.said(computer, "Я вижу, нам придется поработать над нашими отношениями")
    computer.tryOpenDoor(room, -30)
    eddy.changeScene(room, planet)
    ford.changeScene(room, planet)


    println(ford.count(computer, "Это одно из самых агрессивных действий, которые вы можете применить к компьютеру, равносильное тому, чтобы медленно приближаться в темноте к человеку, повторяя: \"Умри... умри... умри...\"."))
    println(eddy.said(computer, "Я вижу, нам придется поработать над нашими отношениями"))
    println(computer.tryOpenDoor(room, -30))
    println(eddy.changeScene(room, planet) + ford.changeScene(room, planet))

}

