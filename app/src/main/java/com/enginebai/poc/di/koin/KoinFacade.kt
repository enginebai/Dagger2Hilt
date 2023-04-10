package com.enginebai.poc.di.koin

import android.content.Context
import android.util.Log
import com.enginebai.core.card.Poker
import com.enginebai.core.di.DomainScope
import com.enginebai.core.util.ColorDefinition
import com.enginebai.core.util.ColorManager
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.domain.DomainTopic
import com.enginebai.poc.data.user.UserDataHelper
import com.example.feature.data.CardRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.UUID
import javax.inject.Inject
import javax.inject.Provider

private const val dynamicFeatureModulesProvider = "com.enginebai.dynamic.di.DynamicFeatureKoinModulesProvider"

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
    private val cardRepositoryProvider: Provider<CardRepository>,
    // For migration
) {
    private val koinApp: KoinApplication

    init {
        stopKoin()
        koinApp = startKoin {
            androidContext(context)
            modules(provideModules())
        }
    }

    val id: UUID by lazy { koinApp.koin.get() }

    private fun provideModules(): List<Module> = mutableListOf(
        appModule(),
        domainModule(),
        featureModule(),
        appColorModule(),

        // For migration
        utilModule()
    ).apply {
        addAll(dynamicFeatureModules())
    }

    // Properties provided from Koin (may from Dagger) back to Dagger:

    private fun appModule() = module {
        single { pokerProvider.get() }
        single { usernameProvider.get() }
        single { userDataHelperProvider.get() }
    }

    private fun utilModule() = module {
        single { UUID.randomUUID() }
    }

    private fun domainModule() = module {
        single { domainTopicProvider.get() }
        single { domainRepositoryProvider.get() }
    }

    private fun featureModule() = module {
        single { cardRepositoryProvider.get() }
    }

    private fun dynamicFeatureModules(): List<Module> = runCatching {
        val module = Class.forName(dynamicFeatureModulesProvider).newInstance()
        (module as KoinModulesProvider).generateModules()
    }.run { getOrNull() ?: throw IllegalStateException() }

    private fun appColorModule() = module {
        single { ColorManager.generateColors().map { ColorDefinition.AppColor(it) } }
    }
}