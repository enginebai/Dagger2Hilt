package com.enginebai.poc.di.koin

import com.enginebai.core.di.DomainScope
import com.enginebai.core.util.ColorDefinition
import com.enginebai.core.util.ColorManager
import com.enginebai.poc.data.DomainApi
import com.enginebai.poc.data.DomainApiInMemory
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.DomainRepositoryImpl
import com.enginebai.poc.data.domain.DomainTopic
import com.enginebai.poc.data.domain.pickRandomTopic
import com.enginebai.poc.data.user.User
import com.example.feature.data.CardApi
import com.example.feature.data.CardRepository
import com.example.feature.data.CardRepositoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import javax.inject.Inject
import javax.inject.Provider
import kotlin.random.Random

@DomainScope
class DomainKoinFacade @Inject constructor(
) : KoinComponent {

    private val domainAggregatorModules = module {
        includes(domainModule(), featureModule(), apiModule())
    }

    fun loadModules() {
        loadKoinModules(domainAggregatorModules)
    }

    fun unloadModules() {
        unloadKoinModules(domainAggregatorModules)
    }

    private fun domainModule() = module {
        single { pickRandomTopic() }
        singleOf(::DomainRepositoryImpl) bind DomainRepository::class
        single {
            val n = Random.nextInt(10000)
            User("$n", "Domain User $n", n)
        }
        singleOf(::CardRepositoryImpl) bind CardRepository::class
    }

    private fun apiModule() = module {
        singleOf(::DomainApiInMemory) bind DomainApi::class
        singleOf(::CardApi)
    }

    private fun featureModule() = module {
        single { ColorDefinition.DomainColor(ColorManager.generateColor()) }
    }
}