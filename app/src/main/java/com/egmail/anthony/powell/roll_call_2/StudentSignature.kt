package com.egmail.anthony.powell.roll_call_2

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
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
import com.egmail.anthony.powell.roll_call_2.Service.FirebaseService
import com.egmail.anthony.powell.roll_call_2.Service.Signature
import com.egmail.anthony.powell.roll_call_2.Service.SignatureService
import com.egmail.anthony.powell.roll_call_2.Service.SignatureSmsService
import kotlinx.android.synthetic.main.activity_student_signature.*

class StudentSignature : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var smsSent: Boolean
        var dbUpdated: Boolean

        setContentView(R.layout.activity_student_signature)
        SignatureService.getDateTimeStamp()
        SignatureService.core(intent.getStringExtra("COURSE_TITLE"),
                student_signature_password_input_field.text.toString())

        student_signature_course_title.text = intent.getStringExtra("COURSE_TITLE")

        student_signature_back_icon.setOnClickListener {
            onBackPressed()
        }

        student_signature_send_button.setOnClickListener {

            checkPermissions{

//                sendSignatureFB(){
//                    dbUpdated = it
//                }
                sendSignatureSMS(SignatureService.signatureForSMS(), R.string.TestNumber.toString(),{
                    smsSent = it
                })

                sendSignatureFB()
//                sendSignatureSMS(SignatureService.signatureForSMS(), R.string.TestNumber.toString())
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

    private fun sendSignatureSMS(withMessage: String, toNumber: String, smsSent:(Boolean)->Unit) {

        val sentPI = PendingIntent.getBroadcast(this, 0, Intent(R.string.smsSent.toString()), 0)
        val deliveredPI = PendingIntent.getBroadcast(this, 0, Intent(R.string.smsDelivered.toString()), 0)

//        SmsManager.getDefault().sendTextMessage(R.string.TestNumber.toString(), null, "Message", sentPI, deliveredPI)

        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(R.string.TestNumber.toString(), null, withMessage, sentPI, deliveredPI)

        var broadcastReceiver = object: BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                when(resultCode){
                    Activity.RESULT_OK -> {
                        println(resultCode)
                        smsSent(true)
                    }
                }
                if(resultCode == Activity.RESULT_OK){
                    AlertDialog.Builder(applicationContext)
                            .setTitle("Signature Recorded")
                            .setIcon(R.drawable.ic_warning_white_36px)
                            .setMessage("Signed in with: $withMessage")
                            .create()
                            .show()

                    Toast.makeText(applicationContext, "Signed in with: $withMessage", Toast.LENGTH_LONG).show()
                    Toast.makeText(applicationContext, "Signature", Toast.LENGTH_LONG).show()
                }
                Toast.makeText(applicationContext, "ResultCode: $resultCode", Toast.LENGTH_LONG).show()
            }
        }
        broadcastReceiver



    }

    private fun sendSignatureFB() {
        FirebaseService.sendSignatureForAttendance {}
    }

    private fun checkPermissions(permissionsGranted: ()->Unit){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE), 1)
        } else {
            permissionsGranted()
        }
    }


}
