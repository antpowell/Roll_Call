package com.egmail.anthony.powell.roll_call_2.Service

import com.egmail.anthony.powell.roll_call_2.Model.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by Powell on 2/20/2018.
 */

//object SignatureService {
//    val course: String = ""
//    val password: String = ""
//    var _email: String? = ""
//    var _lastName: String? = ""
//    var _tNum: String? = ""
//    var _date: String? = ""
//    var _time: String? = ""
//}

data class SignatureService(val course: String = "",
                            val password: String = "",
                            var _email: String? = "",
                            var _lastName: String? = "",
                            var _tNum: String? = "") {
    companion object {
        var date: String = ""
        var dbDate: String = ""
        var time: String = ""
        var course: String = ""
        var password: String = ""

        fun getDateTimeStamp() {
            val dateFormat = SimpleDateFormat("dd/MM/yy")
            val dbDateFormat = SimpleDateFormat("EEE, MMM d, yyyy")
            val timeFormat = SimpleDateFormat("HH:mm")
            date = dateFormat.format(Date())
            dbDate = dbDateFormat.format(Date())
            time = timeFormat.format(Date())
        }

        var signature: SignatureService = SignatureService()

        fun core(course: String = "",
                 password: String = "") {
            this.course = course
            this.password = password
            signature = SignatureService(course, this.password, User.getInstance()._email, User.getInstance()._lastName, User.getInstance()._tNum)
            getDateTimeStamp()
        }

        fun getInstance() = signature

        fun signatureForSMS(): String {
            return "($course,${User.getInstance()._lastName},${User.getInstance()._tNum},$date $time,$password)"
        }

        fun signatureForDB(): HashMap<String, Any> {
            return hashMapOf(
                    "_email" to User.getInstance()._email,
                    "_lastName" to User.getInstance()._lastName,
                    "_tNum" to User.getInstance()._tNum,
                    "POD" to password,
                    "Time" to time)
        }

        fun print(): String {
            val readableSignature = "Name: ${User.getInstance()._lastName}\n" +
                    "TNumber: ${User.getInstance()._tNum}\n" +
                    "Password: $password\n" +
                    "Date Time: $date $time"
            println(readableSignature)
            return readableSignature
        }

    }

}