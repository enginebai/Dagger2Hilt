package com.enginebai.poc.di.koin

import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.domain.DomainTopic
import com.example.feature.data.CardRepository
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import javax.inject.Inject
import javax.inject.Provider

@DomainScope
class DomainKoinFacade @Inject constructor(
    // Domain
    private val domainTopicProvider: Provider<DomainTopic>,
    private val domainRepositoryProvider: Provider<DomainRepository>,
    // Feature
    private val cardRepositoryProvider: Provider<CardRepository>,
    // For migration
) {
    init {
        loadKoinModules(provideModules())
    }

    private fun provideModules(): List<Module> = listOf(
        domainModule(),
        featureModule(),
    )

    private fun domainModule() = module {
        single { domainTopicProvider.get() }
        single { domainRepositoryProvider.get() }
    }

    private fun featureModule() = module {
        single { cardRepositoryProvider.get() }
    }
}