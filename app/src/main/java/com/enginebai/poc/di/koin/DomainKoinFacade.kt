package com.enginebai.poc.di.koin

import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.domain.DomainTopic
import com.enginebai.poc.data.domain.pickRandomTopic
import com.example.feature.data.CardRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import javax.inject.Inject
import javax.inject.Provider

@DomainScope
class DomainKoinFacade @Inject constructor(
): KoinComponent {

    val domainTopic: DomainTopic by inject()

    private val domainAggregatorModules = module {
        includes(domainModule(), featureModule())
    }

    fun loadModules() {
        loadKoinModules(domainAggregatorModules)
    }

    fun unloadModules() {
        unloadKoinModules(domainAggregatorModules)
    }

    private fun domainModule() = module {
        single { pickRandomTopic() }
    }

    private fun featureModule() = module {
    }
}