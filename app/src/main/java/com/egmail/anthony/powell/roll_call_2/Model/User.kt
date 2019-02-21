package com.egmail.anthony.powell.roll_call_2.Model

/**
 * Created by powel on 1/13/2018.
 */
data class User(val _email: String = "",
                val _lastName: String = "",
                val _tNum: String = ""){
    companion object {
        var user:User = User()
        fun createUser(_email: String = "",
                      _lastName: String = "",
                      _tNum: String = ""){
            user = User(_email, _lastName, _tNum)
        }
        fun getInstance() = user
    }
}