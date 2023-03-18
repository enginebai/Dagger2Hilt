package com.enginebai.core.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

// No more use after migrating to @HiltViewModel.
abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val context: Context
        get() = getApplication()

}