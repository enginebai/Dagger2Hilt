package com.enginebai.poc.data

import android.content.Context
import com.enginebai.poc.R

class UserDataHelper(
    private val context: Context, private val randomNumber: Int) {

    fun getUserIdPrefix(): String {
        return "${context.getString(R.string.user_id_prefix)}-$randomNumber: "
    }
}