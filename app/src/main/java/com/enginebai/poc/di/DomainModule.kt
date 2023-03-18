package com.enginebai.poc.di

import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.*
import com.enginebai.poc.data.domain.DomainTopic
import com.enginebai.poc.data.domain.pickRandomTopic
import com.enginebai.poc.util.ColorDefinition
import com.enginebai.poc.util.ColorManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    @Singleton
    fun provideDefaultDomainType(): DomainTopic {
        return pickRandomTopic()
    }

    @Provides
    @Singleton
    fun provideDomainRepository(api: DomainApi): DomainRepository {
        return DomainRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideDomainColor(): ColorDefinition.DomainColor {
        return ColorDefinition.DomainColor(ColorManager.generateColor())
    }
}