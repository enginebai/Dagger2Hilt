package com.enginebai.poc.di

import android.app.Application
import android.content.Context
import com.enginebai.core.card.Poker
import com.enginebai.core.util.ColorDefinition
import com.enginebai.core.util.ColorManager
import com.enginebai.poc.MyApplication
import com.enginebai.poc.data.domain.PokerGame
import com.enginebai.poc.data.user.UserDataHelper
import com.enginebai.poc.di.koin.AppKoinFacade
import dagger.Module
import dagger.Provides
import java.text.DateFormat
import java.util.Calendar
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    fun provideUserName(
        koinFacade: AppKoinFacade
    ): String {
        return koinFacade.username
    }

    @Provides
    @Singleton
    fun provideCardPoker(impl: PokerGame): Poker {
        return impl
    }

    @Provides
    @Singleton
    fun provideUserDataHelper(koinFacade: AppKoinFacade): UserDataHelper {
        return koinFacade.userDataHelper
    }
}