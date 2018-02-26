package com.egmail.anthony.powell.roll_call_2

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.egmail.anthony.powell.roll_call_2.Model.Course
import com.egmail.anthony.powell.roll_call_2.Service.FirebaseService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in_register.*

class SignInRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_register)

        FirebaseService.fetchCourseData {
        }

        register_button_sign_in.setOnClickListener {
            println("Course: ${Course.codes}")
            startActivity(Intent(this, Register::class.java))
        }

        log_in_button_sign_in_register.setOnClickListener {
            val emailEntered = text_input_edit_text_sign_in_register_email.text
            val passwordEntered = text_input_edit_text_sign_in_register_password.text
            val getUserProgressDialog = ProgressDialog(this)
            getUserProgressDialog.setTitle("Searching")
            getUserProgressDialog.setMessage("Looking up $emailEntered")
            getUserProgressDialog.setCancelable(false)


            if (emailEntered.isNullOrEmpty() && passwordEntered.isNullOrEmpty()) {
                AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("Email and Password required.")
                        .setCancelable(true)
                        .create().show()
            } else {
                getUserProgressDialog.show()
                FirebaseService.loginWithEmail(emailEntered.toString(), passwordEntered.toString(),
                        onError = {
                            getUserProgressDialog.dismiss()
                            AlertDialog.Builder(this)
                                    .setIcon(R.drawable.ic_warning_white_36px)
                                    .setTitle("Error")
                                    .setMessage(it?.localizedMessage)
                                    .setCancelable(true).create().show()
                            println("Error: ${it.toString()}")
                        },
                        onSuccess = {
                            getUserProgressDialog.dismiss()
                            if (it) {
                                println("user found")
                                startActivity(Intent(this, CourseList::class.java))
                            }
                        })

            }
        }
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseService.fetchCourseData { startActivity(Intent(this, CourseList::class.java)) }
            Toast.makeText(this, "Current User: ${FirebaseAuth.getInstance().currentUser?.email}", Toast.LENGTH_LONG).show()
        }
    }
}
