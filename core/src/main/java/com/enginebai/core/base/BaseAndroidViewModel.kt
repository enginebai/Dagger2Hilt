package com.enginebai.core.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val context: Context
        get() = getApplication()

}