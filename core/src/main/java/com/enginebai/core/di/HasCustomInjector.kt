package com.enginebai.core.di

// For dynamic feature injection
interface HasCustomInjector {
    fun performInject()
}