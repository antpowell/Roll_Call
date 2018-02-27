package com.egmail.anthony.powell.roll_call_2

import android.Manifest
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.telephony.SmsManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.egmail.anthony.powell.roll_call_2.Model.User
import com.egmail.anthony.powell.roll_call_2.Service.FirebaseService
import com.egmail.anthony.powell.roll_call_2.Service.Signature
import com.egmail.anthony.powell.roll_call_2.Service.SignatureService
import com.egmail.anthony.powell.roll_call_2.Service.SignatureSmsService
import kotlinx.android.synthetic.main.activity_student_signature.*
import kotlin.system.exitProcess

class StudentSignature : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_signature)

        var smsSent: Boolean
        var dbUpdated: Boolean

        SignatureService.getDateTimeStamp()

        student_signature_course_title.text = intent.getStringExtra("COURSE_TITLE")
        student_signature_back_icon.setOnClickListener {
            onBackPressed()
        }
        student_signature_send_button.setOnClickListener {
            main_student_signature_view.hideKeyboard()
            SignatureService.core(intent.getStringExtra("COURSE_TITLE"),
                    student_signature_password_input_field.text.toString())
            checkPermissions {
                sendSignatureSMS(SignatureService.signatureForSMS(), {
                    if (it) {
                        showNotification()
                    }
                })
                sendSignatureFB()
            }
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

    private fun sendSignatureSMS(withMessage: String, smsSent: (Boolean) -> Unit) {

        val sentPI = PendingIntent.getBroadcast(this, 0, Intent(R.string.smsSent.toString()), 0)
        val deliveredPI = PendingIntent.getBroadcast(this, 0, Intent(R.string.smsDelivered.toString()), 0)

        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(getString(R.string.CrinerNumber)/*"8327418926"*/, null, withMessage, sentPI, deliveredPI)
        smsBroadcastReceiver()
    }

    private fun sendSignatureFB() {
        FirebaseService.sendSignatureForAttendance {}
    }

    private fun checkPermissions(permissionsGranted: () -> Unit) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE), 1)
            checkPermissions {}
        } else {
            permissionsGranted()
        }
    }

    private fun smsBroadcastReceiver() {
        val bcr = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                println(resultCode)
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        showNotification()
                    }
                    else -> println(resultCode)
                }
            }
        }
        registerReceiver(bcr, IntentFilter(R.string.smsDelivered.toString()))
    }

    private fun showNotification() {
        Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
        val successSB = Snackbar.make(main_student_signature_view, "Signature Successfully Applied Check Messages to Confirm", Snackbar.LENGTH_INDEFINITE)
        successSB.setAction("OK", {
            sendHome()
        })
        successSB.show()
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun sendHome() {
        val mainIntent = Intent(Intent.ACTION_MAIN)
        mainIntent.addCategory(Intent.CATEGORY_HOME)
        startActivity(mainIntent)
    }

}
