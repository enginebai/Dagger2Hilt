package com.enginebai.poc.di.custom

import com.enginebai.core.di.DomainColorModule
import com.enginebai.core.di.DomainCustomDefineComponent
import com.enginebai.core.util.ColorDefinition
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.user.User
import com.enginebai.poc.di.*
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
])
interface DomainAggregatorModule

@InstallIn(DomainCustomDefineComponent::class)
@EntryPoint
interface DomainCustomComponentEntryPoint : DomainAggregatorModule {
    fun domainRepository(): DomainRepository
    fun domainUser(): User
    fun domainColor(): ColorDefinition.DomainColor
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

    private fun DomainCustomComponentManager.entryPoint() =
        EntryPoints.get(this, DomainCustomComponentEntryPoint::class.java)
}