package com.enginebai.poc.data.user

import android.content.Context
import android.os.Parcelable
import com.enginebai.core.util.ColorDefinition
import com.enginebai.poc.MyApplication
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import javax.inject.Inject

@Parcelize
data class User(
    val id: String,
    var name: String,
    var age: Int
) : Parcelable

object TimeMachine : KoinComponent {

    // NOTE: Dagger does not support injection into Kotlin objects
    // NOTE: Dagger does not support injection into static fields
//    @Inject
//    lateinit var userDataHelper: UserDataHelper

    fun becomeYounger(context: Context) {
        val user =
            get<UserDataHelper>().getUser()
        user.age -= 10
    }
}