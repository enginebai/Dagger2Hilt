package com.enginebai.poc.di

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.enginebai.poc.MyApplication
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
}