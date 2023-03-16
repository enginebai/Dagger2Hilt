package com.enginebai.poc.di

import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.*
import com.enginebai.poc.data.domain.DomainTopic
import com.enginebai.poc.data.domain.pickRandomTopic
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    @DomainScope
    fun provideDefaultDomainType(): DomainTopic {
        return pickRandomTopic()
    }

    @Provides
    @DomainScope
    fun provideDomainRepository(api: DomainApi): DomainRepository {
        return DomainRepositoryImpl(api)
    }
}