package com.enginebai.poc.di

import android.content.Context
import com.enginebai.poc.util.ColorDefinition
import javax.inject.Singleton

interface SingletonComponent {
    val singletonColor: ColorDefinition.SingletonColor
}

interface HasSingletonComponent {
    val singletonComponent: SingletonComponent
}

val Context.singletonComponent: SingletonComponent
    get() = (this.applicationContext as HasSingletonComponent).singletonComponent