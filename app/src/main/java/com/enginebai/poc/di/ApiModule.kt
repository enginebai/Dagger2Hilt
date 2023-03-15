package com.enginebai.poc.di

import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.DomainApi
import com.enginebai.poc.data.DomainApiInMemory
import com.enginebai.poc.data.domain.DomainTopic
import dagger.Module
import dagger.Provides

@Module
class ApiModule {
    @Provides
    @DomainScope
    fun provideDomainApi(defaultType: DomainTopic): DomainApi {
        return DomainApiInMemory(defaultType)
    }
}