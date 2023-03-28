package com.enginebai.dynamic.ui

import android.content.Context
import android.content.Intent
import com.enginebai.poc.ui.dynamic.DynamicActivityFacade

class DynamicActivityFacadeImpl : DynamicActivityFacade {
    override fun start(context: Context) {
        context.startActivity(Intent(context, DynamicActivity::class.java))
    }
}