package mmmlpmsw.testing.lab0.domain

import java.lang.Exception

class ChangeSceneException: Exception() {
    override val message: String?
        get() = "Нельзя сменить комнату на нее саму."
}