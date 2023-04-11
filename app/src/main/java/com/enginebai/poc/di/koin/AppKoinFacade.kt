package com.enginebai.poc.di.koin

import android.content.Context
import com.enginebai.core.card.Poker
import com.enginebai.core.util.ColorDefinition
import com.enginebai.core.util.ColorManager
import com.enginebai.poc.data.user.UserDataHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.*
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

private const val dynamicFeatureModulesProvider = "com.enginebai.dynamic.di.DynamicFeatureKoinModulesProvider"

@Singleton
class AppKoinFacade @Inject constructor(
    private val context: Context,
    // Singleton
    private val pokerProvider: Provider<Poker>,
    private val usernameProvider: Provider<String>,
    private val userDataHelperProvider: Provider<UserDataHelper>,
    private val singletonColorProvider: Provider<ColorDefinition.SingletonColor>
){
    private val koinApp: KoinApplication

    init {
        stopKoin()
        koinApp = startKoin {
            androidContext(context)
            androidLogger(Level.DEBUG)
            modules(provideModules())
        }
    }

    val id: UUID by lazy { koinApp.koin.get() }

    private fun provideModules(): List<Module> = mutableListOf(
        appModule(),
        appColorModule(),

        // For migration
        utilModule()
    ).apply {
        addAll(dynamicFeatureModules())
    }

    private fun appModule() = module {
        single { pokerProvider.get() }
        single { usernameProvider.get() }
        single { userDataHelperProvider.get() }
    }

    private fun utilModule() = module {
        single { UUID.randomUUID() }
    }

    private fun appColorModule() = module {
        single { ColorManager.generateColors().map { ColorDefinition.AppColor(it) } }
        single { singletonColorProvider.get() }
    }

    private fun dynamicFeatureModules(): List<Module> = runCatching {
        val module = Class.forName(dynamicFeatureModulesProvider).newInstance()
        (module as KoinModulesProvider).generateModules()
    }.run { getOrNull() ?: throw IllegalStateException() }
}