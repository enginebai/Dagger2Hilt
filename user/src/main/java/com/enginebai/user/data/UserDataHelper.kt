package com.enginebai.user.data

import android.content.Context
import com.enginebai.user.R

class UserDataHelper(
    private val context: Context, private val randomNumber: Int) {
    fun getUserIdPrefix(): String {
        return "${context.getString(R.string.user_id_prefix)}-$randomNumber: "
    }
}