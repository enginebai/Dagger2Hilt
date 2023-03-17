package com.enginebai.poc.data.user

import android.content.Context
import com.enginebai.poc.MyApplication

data class User(
    val id: String,
    var name: String,
    var age: Int
)

object TimeMachine {
    fun becomeYounger(context: Context) {
        val user =
            (context.applicationContext as MyApplication).appComponent().userDataHelper().getUser()
        user.age -= 10
    }
}