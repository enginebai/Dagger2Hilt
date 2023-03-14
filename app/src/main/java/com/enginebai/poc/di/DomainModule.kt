package com.enginebai.poc.di

import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @DomainScope
    @Provides
    fun provideDefaultDomainType(): DomainType {
        return DomainType.DYNAMIC_PROGRAMMING
    }

    @Provides
    fun provideDomainApi(defaultType: DomainType): DomainApi {
        return DomainApiInMemory(defaultType)
    }

    @Provides
    fun provideDomainRepository(api: DomainApi): DomainRepository {
        return DomainRepositoryImpl(api)
    }
}