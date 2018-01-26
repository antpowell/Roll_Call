package com.egmail.anthony.powell.roll_call_2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_course_list.*

class CourseList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_list)

        course_list_image_view_log_out.setOnClickListener {
//            val intent = Intent(this, SignInRegister::class.java)
            startActivity(Intent(this, SignInRegister::class.java))

        }
    }
}
