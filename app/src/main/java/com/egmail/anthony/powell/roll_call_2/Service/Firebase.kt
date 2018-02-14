package com.egmail.anthony.powell.roll_call_2.Service

import android.widget.Toast
import com.egmail.anthony.powell.roll_call_2.Model.Course
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by powel on 1/13/2018.
 */


object FirebaseService {
    private val mDatabase = FirebaseDatabase.getInstance()
    private val COURSES = FirebaseDatabase.getInstance().getReference("Courses")
    private val USERS = FirebaseDatabase.getInstance().getReference("Users")
    private val ATTENDANCE = FirebaseDatabase.getInstance().getReference("Attendance")
    private val mAuth = FirebaseAuth.getInstance()

    fun loginWithEmail(withEmail: String, andPassword: String, onError: (error: String) -> Unit, onSuccess: (wasSuccessful: Boolean) -> Unit) {
        mAuth.signInWithEmailAndPassword(withEmail, andPassword)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        println(it.result.user.email)
                        onSuccess(it.isSuccessful)
                    } else {
                        println(it.exception)
                        onError(it.exception.toString())
                    }
                }
    }

    fun loginWithGoogle(withGoogle: String) {

    }

    fun loginWithYahoo(withYahoo: String) {

    }


    fun fetchUserData() {
        println("inside fetch user")
        USERS.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                println("ERROR: ${p0.toString().trim()}")
            }

            override fun onDataChange(p0: DataSnapshot?) {

            }
        })
    }

//    fun fetchCoursesData() {
//        println("inside fetch courses")
//        println("First")
//        COURSES.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                println("ERROR: ${p0.toString().trim()}")
//            }
//
//            override fun onDataChange(s: DataSnapshot) {
//                println(s.children)
//                println("Second")
//                Course.codes.addAll(s.child("Codes").value as ArrayList<String>)
//                Course.images = s.child("Images").value as HashMap<String, String>
//            }
//        })
//        println("Third")
//    }

    fun fetchCourseData(callback: () -> Unit) {
        println("inside fetch courses")
        println("Second")
        COURSES.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("ERROR: ${p0.toString().trim()}")
            }

            override fun onDataChange(s: DataSnapshot) {
                println("Third")
                Course.codes.addAll(s.child("Codes").value as ArrayList<String>)
                Course.images = s.child("Images").value as HashMap<String, String>
                callback()
            }
        })
        println("Fourth")
    }


}