package com.enginebai.poc.di

import android.annotation.SuppressLint
import com.enginebai.core.util.ColorManager
import com.enginebai.core.util.RGB
import dagger.Module
import dagger.Provides
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton
import kotlin.random.Random
import kotlin.random.nextInt

@Module
class UtilModule {

    @Singleton
    @Provides
    fun provideId(): UUID {
        return UUID.randomUUID()
    }

    @Provides
    fun provideCalendar(): Calendar {
        return Calendar.getInstance()
    }

    @SuppressLint("SimpleDateFormat")
    @Provides
    fun provideDateFormatter(): DateFormat {
        return SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    }

    @Provides
    fun provideRandomNumber(): Int {
        return Random.nextInt(0..100)
    }

    @Provides
    fun provideRGB(): RGB {
        return ColorManager.generateColor()
    }
}