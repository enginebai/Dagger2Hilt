package com.enginebai.poc.di

import android.app.Application
import android.content.Context
import com.enginebai.core.card.Poker
import com.enginebai.poc.MyApplication
import com.enginebai.poc.data.domain.PokerGame
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
    fun provideCardPoker(impl: PokerGame): Poker {
        return impl
    }
}