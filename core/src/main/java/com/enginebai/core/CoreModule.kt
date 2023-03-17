package com.enginebai.core

object CoreModule {
    private lateinit var delegate: ApplicationDelegate

    fun init(appDelegate: ApplicationDelegate) {
        delegate = appDelegate
    }

    fun getAppDelegate() = delegate
}

interface ApplicationDelegate {
    fun is18YearsOld(): Boolean
}