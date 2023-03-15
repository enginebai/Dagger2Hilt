package com.enginebai.poc.di

import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    @DomainScope
    fun provideDefaultDomainType(): DomainTopic {
        return DomainTopic.DYNAMIC_PROGRAMMING
    }

    @Provides
    @DomainScope
    fun provideDomainRepository(api: DomainApi): DomainRepository {
        return DomainRepositoryImpl(api)
    }
}