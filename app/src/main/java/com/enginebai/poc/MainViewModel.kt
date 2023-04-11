package com.enginebai.poc

import com.enginebai.core.base.BaseViewModel
import com.enginebai.poc.data.user.UserDataHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val complexInjection: ComplexInjection
) : BaseViewModel(), KoinComponent {
    fun greeting(): String {
        complexInjection.log()
        return "Hi, ${get<UserDataHelper>().getUser().name}"
    }
}