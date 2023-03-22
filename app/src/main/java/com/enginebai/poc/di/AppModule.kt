package com.enginebai.poc.di

import android.app.Application
import android.content.Context
import com.enginebai.poc.MyApplication
import com.enginebai.core.util.ColorDefinition
import com.enginebai.core.util.ColorManager
import dagger.Module
import dagger.Provides
import java.text.DateFormat
import java.util.Calendar
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun providesContext(app: MyApplication): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideApplication(app: MyApplication): Application {
        return app
    }

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