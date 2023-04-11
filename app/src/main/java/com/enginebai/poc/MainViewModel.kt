package com.enginebai.poc

import android.app.Application
import com.enginebai.core.base.BaseAndroidViewModel
import com.enginebai.poc.data.user.UserDataHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import javax.inject.Inject

class MainViewModel @Inject constructor(
    application: Application,
    private val complexInjection: ComplexInjection
) : BaseAndroidViewModel(application), KoinComponent {
    fun greeting(): String {
        complexInjection.log()
        return "Hi, ${get<UserDataHelper>().getUser().name}"
    }
}