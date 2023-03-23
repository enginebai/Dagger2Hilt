package com.enginebai.poc.di.custom

import com.enginebai.core.di.DomainCustomComponent
import com.enginebai.core.util.ColorDefinition
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.domain.DomainTopic
import com.enginebai.poc.data.user.User
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(DomainCustomComponent::class)
@EntryPoint
interface DomainCustomComponentEntryPoint {
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