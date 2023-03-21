package com.enginebai.poc.di

import com.enginebai.poc.util.ColorDefinition
import com.enginebai.poc.util.ColorManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.DateFormat
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideUserName(
        calendar: Calendar,
        formatter: DateFormat
    ): String {
        return formatter.format(calendar.time)
    }

    @Provides
    @Singleton
    fun provideAppColor(): List<ColorDefinition.AppColor> {
        val list = mutableListOf<ColorDefinition.AppColor>()
        for (i in 1..5) list.add( ColorDefinition.AppColor(ColorManager.generateColor()))
        return list
    }

    @Provides
    @Singleton
    fun provideSingletonColor(): ColorDefinition.SingletonColor {
        return ColorDefinition.SingletonColor(ColorManager.generateColor())
    }
}