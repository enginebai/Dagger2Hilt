package com.enginebai.poc.di

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Color
import com.enginebai.poc.MyApplication
import com.enginebai.poc.util.ColorDefinition
import com.enginebai.poc.util.ColorManager
import com.enginebai.poc.util.RGB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Singleton
import kotlin.random.Random
import kotlin.random.nextInt

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