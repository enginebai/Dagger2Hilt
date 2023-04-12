package com.enginebai.poc.di

import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.DomainApi
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.DomainRepositoryImpl
import com.enginebai.poc.data.domain.DomainTopic
import com.enginebai.poc.data.domain.pickRandomTopic
import com.enginebai.poc.data.user.User
import com.enginebai.poc.di.koin.DomainKoinFacade
import com.example.feature.data.CardRepository
import com.example.feature.data.CardRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlin.random.Random

@Module
class DomainModule {
    @Provides
    @DomainScope
    fun provideDomainRepository(facade: DomainKoinFacade): DomainRepository {
        return facade.domainRepository
    }
}