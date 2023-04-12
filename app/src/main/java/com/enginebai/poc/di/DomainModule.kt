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
    fun provideDefaultDomainType(facade: DomainKoinFacade): DomainTopic {
        return facade.domainTopic
    }

    @Provides
    @DomainScope
    fun provideDomainRepository(facade: DomainKoinFacade): DomainRepository {
        return facade.domainRepository
    }

    @Provides
    @DomainScope
    fun provideDomainUser(): User {
        val n = Random.nextInt(10000)
        return User("$n", "Domain User $n", n)
    }

    @Provides
    @DomainScope
    fun provideCardRepository(impl: CardRepositoryImpl): CardRepository {
        return impl
    }
}