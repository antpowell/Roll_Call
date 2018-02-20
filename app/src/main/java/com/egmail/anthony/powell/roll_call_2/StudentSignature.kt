package com.egmail.anthony.powell.roll_call_2

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.telephony.SmsManager
import android.widget.Toast
import com.egmail.anthony.powell.roll_call_2.Model.User
import com.egmail.anthony.powell.roll_call_2.Service.Signature
import com.egmail.anthony.powell.roll_call_2.Service.SignatureSmsService
import kotlinx.android.synthetic.main.activity_student_signature.*

class StudentSignature : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_signature)

        student_signature_course_title.text = intent.getStringExtra("COURSE_TITLE")

        student_signature_back_icon.setOnClickListener {
            onBackPressed()
        }

        student_signature_send_button.setOnClickListener{
            println(Signature(intent.getStringExtra("COURSE_TITLE"), student_signature_password_input_field.text.toString()).getSignature())
//            println(User("Name", "UID", "EMail", "tNum", "CS124").getSignature("Password"))
        }

    }

    override fun onResume() {
        super.onResume()
        SignatureSmsService.broadcastReceiversRegistration(applicationContext)
    }

    override fun onPause() {
        super.onPause()
        SignatureSmsService.unregisterBroadcastReceivers(applicationContext)
    }

    fun sendSignatureSMS(withMessage: String, toNumber: String) {

        val sentPI = PendingIntent.getBroadcast(this, 0, Intent(R.string.smsSent.toString()), 0)
        val deliveredPI = PendingIntent.getBroadcast(this, 0, Intent(R.string.smsDelivered.toString()), 0)


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE), 1)
        } else {
            SmsManager.getDefault().sendTextMessage(R.string.TestNumber.toString(), null, "Message", sentPI, deliveredPI)

            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(R.string.TestNumber.toString(), null, "Message", null, null)
        }

    }

    fun sendSignatureFB(withMessage: String, toNumber: String) {

    }



}
