package com.enginebai.poc.di

import android.content.Context
import com.enginebai.poc.util.ColorDefinition
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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