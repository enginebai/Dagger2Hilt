package com.enginebai.poc.util

import androidx.fragment.app.FragmentActivity
import com.enginebai.poc.MyApplication
import com.enginebai.poc.data.user.User
import com.enginebai.poc.data.user.UserDataHelper
import org.koin.android.ext.android.get

fun FragmentActivity.getCurrentUser(): User {
    return get<UserDataHelper>().getUser()
}