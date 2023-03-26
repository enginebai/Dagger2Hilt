package com.enginebai.poc.di.custom

import com.enginebai.core.di.DomainColorModule
import com.enginebai.core.di.DomainCustomDefineComponent
import com.enginebai.core.util.ColorDefinition
import com.enginebai.poc.data.domain.DomainRepository
import com.enginebai.poc.data.user.User
import com.enginebai.poc.di.ApiModule
import com.enginebai.poc.di.DomainModule
import com.enginebai.poc.ui.widget.RandomTopicItem
import com.example.feature.data.CardRepository
import com.example.feature.di.CardApiModule
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(DomainCustomDefineComponent::class)
@Module(includes = [
    DomainModule::class,
    ApiModule::class,
    DomainColorModule::class,
    CardApiModule::class
])
interface DomainAggregatorModule

@InstallIn(DomainCustomDefineComponent::class)
@EntryPoint
interface DomainCustomComponentEntryPoint : DomainAggregatorModule {
    fun domainRepository(): DomainRepository
    fun domainUser(): User
    fun domainColor(): ColorDefinition.DomainColor
    fun cardRepository(): CardRepository

    fun inject(nonAndroidClass: RandomTopicItem)
}

@Module
@InstallIn(ActivityRetainedComponent::class)
object DomainCustomComponentBridge {
    @Provides
    fun provideDomainRepository(
        componentManager: DomainCustomComponentManager
    ): DomainRepository {
        return componentManager.entryPoint()
            .domainRepository()
    }

    @Provides
    fun provideDomainUser(
        componentManager: DomainCustomComponentManager
    ): User {
        return componentManager.entryPoint()
            .domainUser()
    }

    @Provides
    fun provideDomainColor(
        componentManager: DomainCustomComponentManager
    ): ColorDefinition.DomainColor {
        return componentManager.entryPoint()
            .domainColor()
    }

    @Provides
    fun provideCardRepository(
        componentManager: DomainCustomComponentManager
    ): CardRepository {
        return componentManager.entryPoint().cardRepository()
    }

    private fun DomainCustomComponentManager.entryPoint() =
        EntryPoints.get(this, DomainCustomComponentEntryPoint::class.java)
}