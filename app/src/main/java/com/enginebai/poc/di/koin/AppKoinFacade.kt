package com.enginebai.poc.di.koin

import android.annotation.SuppressLint
import android.content.Context
import com.enginebai.core.ListNode
import com.enginebai.core.card.Card
import com.enginebai.core.card.Poker
import com.enginebai.core.util.ColorDefinition
import com.enginebai.core.util.ColorManager
import com.enginebai.poc.ComplexInjection
import com.enginebai.poc.MainViewModel
import com.enginebai.poc.data.domain.PokerGame
import com.enginebai.poc.data.user.UserDataHelper
import com.enginebai.poc.ui.domain.DomainFragmentViewModel
import com.enginebai.poc.util.ColorMixer
import com.example.feature.ui.CardViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
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
) : KoinComponent {

    init {
        stopKoin()
        startKoin {
            androidContext(context)
            androidLogger(Level.DEBUG)
            modules(provideModules())
        }
    }

    val randomNumber: Int by inject()
    val linkedListHead: ListNode<Card> get() = get()

    private fun provideModules(): List<Module> = mutableListOf(
        // NOTE: The order of modules does NOT matter!!
        // UserDataHelper in appModule() uses the dependencies in `utilModule(), it's OK.
        appModule(),
        appColorModule(),
        utilModule(),
        viewModelModules()
    ).apply {
        addAll(dynamicFeatureModules())
    }

    private fun appModule() = module {
        singleOf<Poker>(::PokerGame)
        singleOf(::UserDataHelper)
        singleOf(::ComplexInjection)
    }

    @SuppressLint("SimpleDateFormat")
    private fun utilModule() = module {
        single<UUID> { UUID.randomUUID() }
        factory { Random.nextInt(0..100) }
        single<Calendar> { Calendar.getInstance() }
        single<DateFormat> { SimpleDateFormat("yyyy/MM/dd HH:mm:ss") } bind DateFormat::class
        single<String> { get<DateFormat>().format(get<Calendar>().time) }
    }

    private fun appColorModule() = module {
        single { ColorManager.generateColors().map { ColorDefinition.AppColor(it) } }
        single { ColorDefinition.SingletonColor(ColorManager.generateColor()) }
        single { ColorMixer() }
    }

    private fun dynamicFeatureModules(): List<Module> = runCatching {
        val module = Class.forName(dynamicFeatureModulesProvider).newInstance()
        (module as KoinModulesProvider).generateModules()
    }.run { getOrNull() ?: throw IllegalStateException() }

    private fun viewModelModules() = module {
        viewModelOf(::MainViewModel)
        viewModelOf(::DomainFragmentViewModel)
        viewModelOf(::CardViewModel)
    }
}