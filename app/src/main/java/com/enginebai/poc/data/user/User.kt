package com.enginebai.poc.data.user

import android.content.Context
import android.os.Parcelable
import com.enginebai.core.util.ColorDefinition
import com.enginebai.poc.MyApplication
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class User(
    val id: String,
    var name: String,
    var age: Int
) : Parcelable

object TimeMachine {

    // NOTE: Dagger does not support injection into Kotlin objects
    // NOTE: Dagger does not support injection into static fields
//    @Inject
//    lateinit var userDataHelper: UserDataHelper

    fun becomeYounger(context: Context) {
        val user =
            (context.applicationContext as MyApplication).appComponent().userDataHelper().getUser()
        user.age -= 10
    }
}