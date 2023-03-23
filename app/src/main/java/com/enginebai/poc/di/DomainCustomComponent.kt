package com.enginebai.poc.di

import com.enginebai.core.di.DomainCustomComponent
import com.enginebai.core.util.ColorDefinition
import com.enginebai.poc.data.domain.DomainRepository
import com.enginebai.poc.data.user.User
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.internal.GeneratedComponentManager
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@InstallIn(DomainCustomComponent::class)
@EntryPoint
interface DomainCustomComponentEntryPoint {

    fun domainRepository(): DomainRepository

    fun domainColor(): ColorDefinition.DomainColor

    fun domainUser(): User
}

@Singleton
class DomainCustomComponentManager @Inject constructor(
    private val componentProvider: Provider<DomainCustomComponent.Builder>
) : GeneratedComponentManager<DomainCustomComponent> {

    private var component: DomainCustomComponent = generateComponent()

    override fun generatedComponent(): DomainCustomComponent = component

    fun regenerateComponent() {
        component = generateComponent()
    }

    private fun generateComponent(): DomainCustomComponent {
        return componentProvider.get().build()
    }
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
    fun provideDomainColor(
        componentManager: DomainCustomComponentManager
    ): ColorDefinition.DomainColor {
        return componentManager.entryPoint()
            .domainColor()
    }

    @Provides
    fun provideDomainUser(
        componentManager: DomainCustomComponentManager
    ): User {
        return componentManager.entryPoint()
            .domainUser()
    }

    private fun DomainCustomComponentManager.entryPoint() =
        EntryPoints.get(this, DomainCustomComponentEntryPoint::class.java)
}