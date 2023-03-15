package com.enginebai.poc.di

import android.annotation.SuppressLint
import dagger.Module
import dagger.Provides
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.random.Random
import kotlin.random.nextInt

@Module
class AppModule {
    @Provides
    fun provideUserName(
        calendar: Calendar,
        formatter: DateFormat
    ): String {
        return formatter.format(calendar.time)
    }
}