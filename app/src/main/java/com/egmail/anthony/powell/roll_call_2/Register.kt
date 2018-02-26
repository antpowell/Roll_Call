package com.egmail.anthony.powell.roll_call_2

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.egmail.anthony.powell.roll_call_2.Service.FirebaseService
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_sign_in_register.*

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_image_view_close.setOnClickListener {
            finish()
        }

        button_register.setOnClickListener {
            val message = "Last Name: ${text_input_edit_text_register_last_name.text}\n" +
                    "T Number: T${text_input_edit_text_register_t_num.text}\n" +
                    "Email: ${text_input_edit_text_register_email.text}\n" +
                    "Password is hidden of course."
            AlertDialog.Builder(this)
                    .setTitle("Please Verify New Account Information.")
                    .setMessage(message)
                    .setPositiveButton("Correct, Continue", { _, _ ->
                        val registerUserProgressDialog = ProgressDialog(this)
                        registerUserProgressDialog.setTitle("New Account")
                        registerUserProgressDialog.setMessage("Creating a new account for ${text_input_edit_text_register_email.text}")
                        registerUserProgressDialog.show()

                        FirebaseService.createUser(text_input_edit_text_register_email.text.toString(), text_input_edit_text_register_password.text.toString(),
                                {if (it) {
                                    registerUserProgressDialog.dismiss()
                                    println("Successful: $it")
                                    startActivity(Intent(this, CourseList::class.java))
                                }},
                                {registerUserProgressDialog.dismiss()
                                    AlertDialog.Builder(this)
                                            .setIcon(R.drawable.ic_warning_white_36px)
                                            .setTitle("Error")
                                            .setMessage(it?.localizedMessage)
                                            .create().show()
                                })

                    })
                    .setNegativeButton("Cancel",{_, _ ->
                        finish()
                    })
                    .show()
//
//
        }
    }
}
