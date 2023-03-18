package com.enginebai.poc.di

import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.DomainApi
import com.enginebai.poc.data.DomainApiInMemory
import com.enginebai.poc.data.domain.DomainTopic
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideDomainApi(defaultType: DomainTopic): DomainApi {
        return DomainApiInMemory(defaultType)
    }
}