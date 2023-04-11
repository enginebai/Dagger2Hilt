package com.enginebai.poc.di.koin

import android.annotation.SuppressLint
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
import org.koin.dsl.bind
import org.koin.dsl.module
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.random.Random
import kotlin.random.nextInt

private const val dynamicFeatureModulesProvider = "com.enginebai.dynamic.di.DynamicFeatureKoinModulesProvider"

@Singleton
class AppKoinFacade @Inject constructor(
    private val context: Context,
    // Singleton
    private val pokerProvider: Provider<Poker>,
    private val userDataHelperProvider: Provider<UserDataHelper>,
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
    val randomNumber: Int by lazy { koinApp.koin.get() }
    val calendar: Calendar by lazy { koinApp.koin.get() }
    val dateFormat: DateFormat by lazy { koinApp.koin.get() }

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
        single { userDataHelperProvider.get() }
    }

    @SuppressLint("SimpleDateFormat")
    private fun utilModule() = module {
        single { UUID.randomUUID() }
        factory { Random.nextInt(0..100) }
        single { Calendar.getInstance() }
        single { SimpleDateFormat("yyyy/MM/dd HH:mm:ss") } bind DateFormat::class
    }

    private fun appColorModule() = module {
        single { ColorManager.generateColors().map { ColorDefinition.AppColor(it) } }
        single { ColorDefinition.SingletonColor(ColorManager.generateColor()) }
    }

    private fun dynamicFeatureModules(): List<Module> = runCatching {
        val module = Class.forName(dynamicFeatureModulesProvider).newInstance()
        (module as KoinModulesProvider).generateModules()
    }.run { getOrNull() ?: throw IllegalStateException() }
}