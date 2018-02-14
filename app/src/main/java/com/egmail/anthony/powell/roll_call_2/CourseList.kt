package com.egmail.anthony.powell.roll_call_2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.egmail.anthony.powell.roll_call_2.Service.FirebaseService
import kotlinx.android.synthetic.main.activity_course_list.*
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class CourseList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_list)

        println("Fetching Firebase")
        FirebaseService.fetchUserData()

        course_list_recycler_view_listing.layoutManager = LinearLayoutManager(this)
        course_list_recycler_view_listing.adapter = CourseListAdapter(this)

        course_list_image_view_log_out.setOnClickListener {
            startActivity(Intent(this, SignInRegister::class.java))
        }

    }
}

