package com.enginebai.poc.util

import androidx.fragment.app.FragmentActivity
import com.enginebai.poc.MyApplication
import com.enginebai.poc.data.user.User

fun FragmentActivity.getCurrentUser(): User {
    return (this.application as MyApplication).component().userDataHelper().getUser()
}