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
    fun provideUserName(
        calendar: Calendar,
        formatter: DateFormat
    ): String {
        return formatter.format(calendar.time)
    }
}