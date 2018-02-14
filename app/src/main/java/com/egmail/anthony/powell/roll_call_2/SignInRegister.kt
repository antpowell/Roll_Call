package com.egmail.anthony.powell.roll_call_2

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.egmail.anthony.powell.roll_call_2.Model.Course
import com.egmail.anthony.powell.roll_call_2.Service.FirebaseService
import kotlinx.android.synthetic.main.activity_sign_in_register.*

class SignInRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_register)

        println("Start")
        FirebaseService.fetchCourseData {
            println("First")
        }

        register_button_sign_in.setOnClickListener {
            println("Course: ${Course.codes}")
            startActivity(Intent(this, Register::class.java))
        }

        log_in_button_sign_in_register.setOnClickListener {
            val emailEntered = text_input_edit_text_sign_in_register_email.text
            val passwordEntered = text_input_edit_text_sign_in_register_password.text

            AlertDialog.Builder(this)
                    .setTitle("Title")
                    .setMessage("Email: %s\nPassword: %s".format(emailEntered, passwordEntered))
                    .setPositiveButton("Ok", { dialogInterface, i ->
                        if (emailEntered.isNullOrEmpty() && passwordEntered.isNullOrEmpty()) {
                            AlertDialog.Builder(this)
                                    .setTitle("Error")
                                    .setMessage("Email and Password required.")
                                    .setCancelable(true)
                                    .create().show()
                        } else {
                            FirebaseService.loginWithEmail(emailEntered.toString(), passwordEntered.toString(),
                                    onError = {
                                        println("Error: ${it}")
                                    },
                                    onSuccess = {
                                        if (it) {
                                            println("user found")
                                            startActivity(Intent(this, CourseList::class.java))
                                        }
                                    })
                        }
                    })
                    .create()
                    .show()
        }
    }
}
