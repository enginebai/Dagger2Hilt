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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Singleton
import kotlin.random.Random
import kotlin.random.nextInt

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
    fun provideAppColor(): ColorDefinition.AppColor {
        return ColorDefinition.AppColor(ColorManager.generateColor())
    }

    @Provides
    @Singleton
    fun provideSingletonColor(): ColorDefinition.SingletonColor {
        return ColorDefinition.SingletonColor(ColorManager.generateColor())
    }
}