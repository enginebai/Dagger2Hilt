package com.enginebai.poc.data.user

import android.content.Context
import android.os.Parcelable
import com.enginebai.poc.MyApplication
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    var name: String,
    var age: Int
) : Parcelable

object TimeMachine {
    fun becomeYounger(context: Context) {
        val user =
            (context.applicationContext as MyApplication).appComponent().userDataHelper().getUser()
        user.age -= 10
    }
}