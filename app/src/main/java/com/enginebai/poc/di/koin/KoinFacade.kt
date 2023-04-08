package com.enginebai.poc.di.koin

import android.content.Context
import com.enginebai.core.card.Poker
import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.domain.DomainTopic
import com.enginebai.poc.data.user.UserDataHelper
import com.example.feature.data.CardRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import javax.inject.Inject
import javax.inject.Provider

@DomainScope
class KoinFacade @Inject constructor(
    private val context: Context,
    // Singleton
    private val pokerProvider: Provider<Poker>,
    private val usernameProvider: Provider<String>,
    private val userDataHelperProvider: Provider<UserDataHelper>,
    // Domain
    private val domainTopicProvider: Provider<DomainTopic>,
    private val domainRepositoryProvider: Provider<DomainRepository>,
    // Feature
    private val cardRepositoryProvider: Provider<CardRepository>
) {
    private val koinApp: KoinApplication

    init {
        stopKoin()
        koinApp = startKoin {
            androidContext(context)
            modules(appModule())
            modules(domainModule())
            modules(featureModule())
        }
    }

    private fun appModule() = module {
        factory { pokerProvider.get() }
        factory { usernameProvider.get() }
        factory { userDataHelperProvider.get() }
    }

    private fun domainModule() = module {
        factory { domainTopicProvider.get() }
        factory { domainRepositoryProvider.get() }
    }

    private fun featureModule() = module {
        factory { cardRepositoryProvider.get() }
    }
}