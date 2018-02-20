package com.egmail.anthony.powell.roll_call_2.Service

import android.os.Build
import com.egmail.anthony.powell.roll_call_2.Model.User
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

/**
 * Created by powel on 1/13/2018.
 */


data class Signature(val course: String, val password: String){


    private fun setDateTimeStamp(): Pair<String, String> {
        val dateFormat = SimpleDateFormat("dd/MM/yy")
        val timeFormat = SimpleDateFormat("HH:mm")
        val date = dateFormat.format(Date())
        val time = timeFormat.format(Date())
        println("$date, $time")
        return Pair(date.toString(), time.toString())
    }

    private fun makeSignature(forSMS: Boolean):String{
        val (date, time) =  setDateTimeStamp()
        var sig = ""
        if (forSMS){
            sig = "($course,${User.getInstance()._lastName},${User.getInstance()._tNum},$date $time,$password)"
        }

       return "($course,${User.getInstance()._lastName},${User.getInstance()._tNum},$date $time,$password)"
    }

    fun getSignature():String{
        return makeSignature(true)
    }
}