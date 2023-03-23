package com.enginebai.core.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

@Deprecated("No more use after migrating to @HiltViewModel, just use BaseViewModel()")
abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val context: Context
        get() = getApplication()

}