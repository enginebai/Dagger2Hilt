package com.enginebai.poc.data.user

import android.content.Context
import com.enginebai.poc.R
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataHelper @Inject constructor(
    private val context: Context,
    private val id: UUID,
    private val name: String,
    private val age: Int) {

    private lateinit var currentUser: User

    fun generateNewUser() {
        currentUser = User(
            "${getUserIdPrefix()}${id.leastSignificantBits}",
            name,
            age
        )
    }

    fun getUser(): User = currentUser

    private fun getUserIdPrefix(): String {
        return context.getString(R.string.user_id_prefix)
    }
}