package rango1.vku.sqlite

import java.util.*

data class StudentModel (
    var id: Int = getAutoID(),
    var name: String = "",
    var email: String = "",
    var contact: String = "",
    var address: String = ""
)
{
    companion object {
        fun getAutoID(): Int {
        val random = Random()
            return random.nextInt(100)
        }
    }

}