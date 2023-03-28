package com.enginebai.dynamic

import android.app.Application
import com.enginebai.core.Initializer
import com.enginebai.dynamic.ui.DynamicActivityFacadeImpl
import com.enginebai.poc.ui.dynamic.DynamicFeatureFacadeModule

// Use reflection to create the instance
class DynamicFeatureInitializer : Initializer {
    override fun initialize(application: Application) {
        DynamicFeatureFacadeModule.activityFacade = DynamicActivityFacadeImpl()
    }
}