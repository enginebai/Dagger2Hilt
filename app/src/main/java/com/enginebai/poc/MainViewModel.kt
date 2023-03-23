package com.enginebai.poc

import android.app.Application
import android.content.Context
import com.enginebai.core.base.BaseAndroidViewModel
import com.enginebai.core.base.BaseViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : BaseViewModel() {
    fun greeting(): String {
        val app = (context.applicationContext as MyApplication)
        return "Hi, ${app.appComponent().userDataHelper().getUser().name}"
    }
}