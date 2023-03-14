package com.enginebai.poc.data

import android.content.Context
import com.enginebai.poc.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataHelper @Inject constructor(
    private val context: Context,
    private val name: String,
    private val randomNumber: Int) {

    private lateinit var currentUser: User

    fun generateNewUser() {
        currentUser = User(
            getUserIdPrefix() + name,
            randomNumber
        )
    }

    fun getUser(): User = currentUser

    private fun getUserIdPrefix(): String {
        return "${context.getString(R.string.user_id_prefix)}-$randomNumber: "
    }
}