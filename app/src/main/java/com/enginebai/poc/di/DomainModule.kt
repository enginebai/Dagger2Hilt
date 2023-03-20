package com.enginebai.poc.di

import com.enginebai.core.di.CustomComponentBridge
import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.*
import com.enginebai.poc.data.domain.*
import com.enginebai.poc.util.ColorDefinition
import com.enginebai.poc.util.ColorManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

@Module
@InstallIn(DomainCustomComponent::class)
class DomainModule {
    @Provides
    @DomainScope
    fun provideDefaultDomainType(): DomainTopic {
        return pickRandomTopic()
    }

    @Provides
    @DomainScope
    @CustomComponentBridge
    fun provideDomainRepository(api: DomainApi): DomainRepository {
        return DomainRepositoryImpl(api)
    }

    @Provides
    @DomainScope
    @CustomComponentBridge
    fun provideDomainColor(): ColorDefinition.DomainColor {
        return ColorDefinition.DomainColor(ColorManager.generateColor())
    }
}