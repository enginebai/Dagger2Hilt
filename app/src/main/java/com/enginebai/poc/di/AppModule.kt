package com.enginebai.poc.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.DateFormat
import java.util.Calendar
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
}