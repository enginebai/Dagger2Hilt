package com.enginebai.poc.di

import com.enginebai.core.di.DomainCustomComponent
import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.domain.DomainApi
import com.enginebai.poc.data.domain.DomainApiInMemory
import com.enginebai.poc.data.domain.DomainTopic
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

@Module
@InstallIn(DomainCustomComponent::class)
class ApiModule {
    @Provides
    @DomainScope
    fun provideDomainApi(defaultType: DomainTopic): DomainApi {
        return DomainApiInMemory(defaultType)
    }
}