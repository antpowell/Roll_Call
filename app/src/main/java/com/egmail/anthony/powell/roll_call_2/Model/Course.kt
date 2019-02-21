package com.egmail.anthony.powell.roll_call_2.Model

import android.arch.lifecycle.ViewModel

/**
 * Created by powel on 1/13/2018.
 */

//data class Course(val codes: ArrayList<String>, val images: HashMap<String, String>){
//    companion object {
//        val codes: ArrayList<String> = this.codes
//        val images: HashMap<String, String> = this.images
//    }
//}

object Course {
    var codes: ArrayList<String> = ArrayList()
    var images: HashMap<String, String> = HashMap()
}

//class Course{
//
//    var codes:ArrayList<String> by Delegates.notNull()
//    var images: HashMap<String, String> by Delegates.notNull()
//
//    private constructor(codes:ArrayList<String>, images: HashMap<String, String>){
//        var codes:ArrayList<String> = codes
//        var images: HashMap<String, String> = images
//    }
//
//    private constructor()
//
//    companion object {
//        val instance: Course by lazy { Course() }
//        var codes:ArrayList<String> by Delegates.notNull()
//        var images: HashMap<String, String> by Delegates.notNull()
//
//    }
//}

//class Course : ViewModel() {
//    var codes: ArrayList<String> = ArrayList()
//    var images: HashMap<String, String> = HashMap()
//}
