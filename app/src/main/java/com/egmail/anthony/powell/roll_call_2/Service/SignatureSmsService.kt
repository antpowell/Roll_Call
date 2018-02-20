package com.egmail.anthony.powell.roll_call_2.Service

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.SmsManager
import android.widget.Toast
import com.egmail.anthony.powell.roll_call_2.R
import kotlinx.coroutines.experimental.withContext

/**
 * Created by Powell on 2/14/2018.
 */

class SignatureSmsService {
    companion object {

        var returnMessage: String = ""
        private val smsSentReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
//                        Toast.makeText(context, "Signature Sent Successfully!", Toast.LENGTH_SHORT).show()
                        returnMessage = "Signature Sent Successfully!"
                    }
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
//                        Toast.makeText(context, "Error: Sending Signature!", Toast.LENGTH_SHORT).show()
                        returnMessage = "Error: Sending Signature!"
                    }
                    SmsManager.RESULT_ERROR_NO_SERVICE -> {
//                        Toast.makeText(context, "Error: No Services On This Device!", Toast.LENGTH_SHORT).show()
                        returnMessage = "Error: No Services On This Device!"
                    }
                    SmsManager.RESULT_ERROR_RADIO_OFF -> {
//                        Toast.makeText(context, "Error: Radio Off!", Toast.LENGTH_SHORT).show()
                        returnMessage = "Error: Radio Off!"
                    }
                }
            }
        }
        private val smsDeliveredReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
//                        Toast.makeText(context, "Signature Delivered Successfully!", Toast.LENGTH_SHORT).show()
                        returnMessage = "Signature Delivered Successfully!"
                    }
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
//                        Toast.makeText(context, "Error: Sending Signature!", Toast.LENGTH_SHORT).show()
                        returnMessage = "Error: Sending Signature!"
                    }
                    SmsManager.RESULT_ERROR_NO_SERVICE -> {
//                        Toast.makeText(context, "Error: No Services On This Device!", Toast.LENGTH_SHORT).show()
                        returnMessage = "Error: No Services On This Device!"
                    }
                    SmsManager.RESULT_ERROR_RADIO_OFF -> {
//                        Toast.makeText(context, "Error: Radio Off!", Toast.LENGTH_SHORT).show()
                        returnMessage = "Error: Radio Off!"
                    }
                }
            }
        }

        fun broadcastReceiversRegistration(context: Context) {
            context.registerReceiver(smsSentReceiver, IntentFilter(R.string.smsSent.toString()))
            context.registerReceiver(smsDeliveredReceiver, IntentFilter(R.string.smsDelivered.toString()))
        }

        fun unregisterBroadcastReceivers(context: Context){
            context.unregisterReceiver(smsSentReceiver)
            context.unregisterReceiver(smsDeliveredReceiver)
        }

    }
}