package com.enginebai.poc

import android.app.Application
import com.enginebai.core.base.BaseAndroidViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    application: Application,
    private val complexInjection: ComplexInjection
) : BaseAndroidViewModel(application) {
    fun greeting(): String {
        val app = (context.applicationContext as MyApplication)
        complexInjection.log()
        return "Hi, ${app.appComponent().userDataHelper().getUser().name}"
    }
}