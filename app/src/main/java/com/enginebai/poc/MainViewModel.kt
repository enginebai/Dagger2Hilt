package com.enginebai.poc

import android.app.Application
import com.enginebai.core.base.BaseAndroidViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application) : BaseAndroidViewModel(application) {
    fun greeting(): String {
        val app = (context.applicationContext as MyApplication)
        return "Hi, ${app.component().userDataHelper().getUser().name}"
    }
}