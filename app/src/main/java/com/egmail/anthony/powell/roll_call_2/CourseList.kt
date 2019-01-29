package com.egmail.anthony.powell.roll_call_2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Toast
import com.egmail.anthony.powell.roll_call_2.Model.Course
import com.egmail.anthony.powell.roll_call_2.Service.FirebaseService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_course_list.*
import kotlinx.android.synthetic.main.course_item.*

class CourseList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_list)

        setSupportActionBar(course_list_toolbar)

        println("Fetching Firebase")
        FirebaseService.fetchUserData(
                userFound = {println("User found data: $it")},
                userNotFound = { println(it) })

        course_list_recycler_view_listing.layoutManager = LinearLayoutManager(this)
        course_list_recycler_view_listing.adapter = CourseListAdapter(this)

        course_list_image_view_log_out.setOnClickListener {
            FirebaseService.signOut()
            finish()
//            startActivity(Intent(this, SignInRegister::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        return true
    }

}

