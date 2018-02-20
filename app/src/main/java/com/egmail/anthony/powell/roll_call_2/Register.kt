package com.egmail.anthony.powell.roll_call_2

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
            AlertDialog.Builder(this)
                    .setTitle("Please Verify New Account Information.")
                    .setMessage("Last Name: ${text_input_edit_text_register_last_name.text}\n" +
                            "T Number: T${text_input_edit_text_register_t_num.text}\n" +
                            "Email: ${text_input_edit_text_register_email.text}\n" +
                            "Password is hidden of course.")
                    .setPositiveButton("Correct, Continue", { _, i ->
                        FirebaseService.createUser(text_input_edit_text_register_email.text.toString(), text_input_edit_text_register_password.text.toString(),
                                {if (it) {
                                    println("Successful: $it")
                                    startActivity(Intent(this, CourseList::class.java))
                                }},
                                {println(it)})

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
