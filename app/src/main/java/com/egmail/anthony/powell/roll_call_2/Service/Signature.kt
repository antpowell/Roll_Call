package com.egmail.anthony.powell.roll_call_2.Service

import com.egmail.anthony.powell.roll_call_2.Model.User
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by powel on 1/13/2018.
 */


data class Signature(val course: String,
                     val password: String
//                     var _email: String?,
//                     var _lastName: String?,
//                     var _tNum: String?,
//                     var _date: String?,
//                     var _time: String?
) {
    var date: String = ""
    var dbDate: String = ""
    var time: String = ""

    private fun setDateTimeStamp(): Pair<String, String> {
        val dateFormat = SimpleDateFormat("dd/MM/yy")
        val dbDateFormat = SimpleDateFormat("EEE, MMM d, yyyy")
        val timeFormat = SimpleDateFormat("HH:mm")
        date = dateFormat.format(Date())
        dbDate = dbDateFormat.format(Date())
        time = timeFormat.format(Date())
//        println("$date, $time")
        return Pair(date.toString(), time.toString())
    }

    private fun makeSignature(forSMS: Boolean): Any {
        return if (forSMS) {
            "($course,${User.getInstance()._lastName},${User.getInstance()._tNum},$date $time,$password)"
        } else {
            makeSignatureForDB()
        }
    }

    private fun makeSignatureForDB(): HashMap<String, Any>{
        val signature: HashMap<String, Any> = hashMapOf(course to hashMapOf(dbDate to hashMapOf("POD" to password,
                "Time" to time,
                "_email" to User.getInstance()._email,
                "_lastName" to User.getInstance()._lastName,
                "_tNum" to User.getInstance()._tNum)))
        return signature
    }

    fun getSignature(forSMS:Boolean): Any {
        setDateTimeStamp()
        return makeSignature(forSMS)
    }
}