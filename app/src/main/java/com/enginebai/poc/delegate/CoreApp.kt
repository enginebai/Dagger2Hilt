package com.enginebai.poc.delegate

import com.enginebai.core.ApplicationDelegate
import com.enginebai.poc.MyApplication

class CoreApp(private val app: MyApplication) : ApplicationDelegate {
    override fun is18YearsOld(): Boolean {
        return app.appComponent().userDataHelper().getUser().age >= 18
    }
}