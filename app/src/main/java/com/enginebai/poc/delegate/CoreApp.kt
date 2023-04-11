package com.enginebai.poc.delegate

import com.enginebai.core.ApplicationDelegate
import com.enginebai.poc.MyApplication
import com.enginebai.poc.data.user.UserDataHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class CoreApp(private val app: MyApplication) : ApplicationDelegate, KoinComponent {
    override fun is18YearsOld(): Boolean {
        return get<UserDataHelper>().getUser().age >= 18
    }
}