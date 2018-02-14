package com.egmail.anthony.powell.roll_call_2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_student_signature.*

class StudentSignature : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_signature)

        student_signature_course_title.text = intent.getStringExtra("COURSE_TITLE")

        student_signature_back_icon.setOnClickListener {
            onBackPressed()
        }


    }
}
