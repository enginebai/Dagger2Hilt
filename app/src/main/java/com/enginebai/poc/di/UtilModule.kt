package com.enginebai.poc.di

import android.annotation.SuppressLint
import com.enginebai.poc.di.koin.DomainKoinFacade
import com.enginebai.poc.di.koin.SingletonKoinFacade
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
    fun provideId(koinFacade: SingletonKoinFacade): UUID {
        return koinFacade.id
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
}