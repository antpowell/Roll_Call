package com.egmail.anthony.powell.roll_call_2

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics

class SignInRegister : AppCompatActivity() {
    companion object {
        val displayMetric = DisplayMetrics()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_register)

    }
}
