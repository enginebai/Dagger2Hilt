package com.enginebai.poc.di

import android.content.Context
import com.enginebai.core.util.ColorDefinition

interface MySingletonComponent {
    val singletonColor: ColorDefinition.SingletonColor
}

interface HasSingletonComponent {
    val singletonComponent: MySingletonComponent
}

val Context.singletonComponent: MySingletonComponent
    get() = (this.applicationContext as HasSingletonComponent).singletonComponent