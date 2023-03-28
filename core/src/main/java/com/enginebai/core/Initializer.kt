package com.enginebai.core

import android.app.Application

interface Initializer {
    fun initialize(application: Application)
}