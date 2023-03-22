package com.enginebai.poc.di

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.enginebai.core.util.ColorDefinition

@InstallIn(SingletonComponent::class)
@EntryPoint
interface MySingletonComponent {
    val singletonColor: ColorDefinition.SingletonColor
}

interface HasSingletonComponent {
    val singletonComponent: MySingletonComponent
}

val Context.singletonComponent: MySingletonComponent
    get() = (this.applicationContext as HasSingletonComponent).singletonComponent