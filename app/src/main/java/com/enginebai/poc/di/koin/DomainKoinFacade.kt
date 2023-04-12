package com.enginebai.poc.di.koin

import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.domain.DomainTopic
import com.enginebai.poc.data.domain.pickRandomTopic
import com.example.feature.data.CardRepository
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import javax.inject.Inject
import javax.inject.Provider

@DomainScope
class DomainKoinFacade @Inject constructor(
) {
    init {
        loadKoinModules(provideModules())
    }

    private fun provideModules(): List<Module> = listOf(
        domainModule(),
        featureModule(),
    )

    private fun domainModule() = module {
    }

    private fun featureModule() = module {
    }
}