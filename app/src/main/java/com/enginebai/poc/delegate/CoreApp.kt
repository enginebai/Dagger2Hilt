package com.enginebai.poc.delegate

import com.enginebai.core.ApplicationDelegate
import com.enginebai.poc.MyApplication

class CoreApp(private val app: MyApplication) : ApplicationDelegate {
    override fun is18YearsOld(): Boolean {
        return app.component().userDataHelper().getUser().age >= 18
    }

    override fun isStrongest(): Boolean {
        return app.component().userDataHelper().getUser().body.isStrongest()
    }
}