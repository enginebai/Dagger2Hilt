package com.enginebai.poc.di

import android.annotation.SuppressLint
import android.content.Context
import com.enginebai.poc.data.UserDataHelper
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
    fun provideCalendar(): Calendar {
        return Calendar.getInstance()
    }

    @SuppressLint("SimpleDateFormat")
    @Provides
    fun provideDateFormatter(): DateFormat {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    }

    @Provides
    fun provideUserName(
        calendar: Calendar,
        formatter: DateFormat
    ): String {
        return formatter.format(calendar.time)
    }

    @Provides
    fun provideRandomNumber(): Int {
        return Random.nextInt(0..100)
    }
}