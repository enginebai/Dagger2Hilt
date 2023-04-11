package com.enginebai.poc.di

import android.annotation.SuppressLint
import com.enginebai.poc.di.koin.AppKoinFacade
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
    fun provideId(koinFacade: AppKoinFacade): UUID {
        return koinFacade.id
    }

    @Provides
    fun provideRandomNumber(koinFacade: AppKoinFacade): Int {
        return koinFacade.randomNumber
    }
}