package com.egmail.anthony.powell.roll_call_2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
            startActivity(Intent(this, CourseList::class.java))
        }

    }
}
