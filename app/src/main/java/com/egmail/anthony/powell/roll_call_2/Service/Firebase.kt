package com.egmail.anthony.powell.roll_call_2.Service

import com.egmail.anthony.powell.roll_call_2.Model.Course
import com.egmail.anthony.powell.roll_call_2.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by powel on 1/13/2018.
 */


object FirebaseService {
    private val mDatabase = FirebaseDatabase.getInstance()
    private val COURSES = FirebaseDatabase.getInstance().getReference("Courses")
    private val USERS = FirebaseDatabase.getInstance().getReference("Users")
    private val ATTENDANCE = FirebaseDatabase.getInstance().getReference("Attendance")
    private val TESTER = FirebaseDatabase.getInstance().getReference("Tester")
    private val mAuth = FirebaseAuth.getInstance()

    fun loginWithEmail(withEmail: String, andPassword: String, onError: (error: Exception?) -> Unit, onSuccess: (wasSuccessful: Boolean) -> Unit) {
        mAuth.signInWithEmailAndPassword(withEmail, andPassword)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onSuccess(it.isSuccessful)
                    } else {
                        println(it.exception?.message)
                        onError(it.exception)
                    }
                }
    }

    fun loginWithGoogle(withGoogle: String) {

    }

    fun loginWithYahoo(withYahoo: String) {

    }

    fun createUser(withEmail: String, andPassword: String, userCreated: (Boolean) -> Unit, userNotCreated: (Exception?) -> Unit) {
        mAuth.createUserWithEmailAndPassword(withEmail, andPassword)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        userCreated(it.isSuccessful)
                    } else {
                        userNotCreated(it.exception)
                    }
                }
    }

    fun resetUserPassword(fromEmail: String) {
        mAuth.sendPasswordResetEmail(fromEmail)
                .addOnCompleteListener {
                    if (it.isSuccessful) {

                    }
                }
    }

    fun fetchUserData(userFound: (User) -> Unit, userNotFound: (String) -> Unit) {
        println("Looking user up by UID")


        if (!mAuth.currentUser?.uid.isNullOrEmpty()) {
            val postListener =  object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.hasChild(mAuth.currentUser?.uid!!)) {
                        val holder = p0.child(mAuth.currentUser?.uid!!)
                        User.createUser(holder.child("_email").value.toString(),
                                holder.child("_lastName").value.toString(),
                                holder.child("_tNum").value.toString())
                        userFound(User.getInstance())
                    } else {
                        userNotFound("No user found for that UID.")
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    userNotFound("Error: ${p0.toString()}")
                }
            }
            USERS.addListenerForSingleValueEvent(postListener);
        }
    }

    fun fetchCourseData(callback: () -> Unit) {
        COURSES.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                println("ERROR: ${p0.toString().trim()}")
            }

            override fun onDataChange(s: DataSnapshot) {
                Course.codes = arrayListOf()
                Course.images = hashMapOf()
                Course.codes.addAll(s.child("Codes").value as ArrayList<String>)
                Course.images = s.child("Images").value as HashMap<String, String>
                callback()
            }
        })
    }

    fun sendSignatureForAttendance( complete: () -> Unit){

        ATTENDANCE
                .child(SignatureService.getInstance().course)
                .child(SignatureService.dbDate)
                .child(User.getInstance()._tNum)
                .setValue(SignatureService.signatureForDB())
                .addOnCompleteListener {
            complete()
        }
    }

    fun signOut(){mAuth.signOut()}

}