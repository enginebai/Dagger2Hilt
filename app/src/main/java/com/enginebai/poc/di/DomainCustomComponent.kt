package com.enginebai.poc.di

import com.enginebai.core.di.CustomComponentBridge
import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.domain.DomainRepository
import com.enginebai.poc.util.ColorDefinition
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.internal.GeneratedComponentManager
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@DomainScope
@DefineComponent(parent = SingletonComponent::class)
interface DomainCustomComponent {
    @DefineComponent.Builder
    interface Builder {
        fun build(): DomainCustomComponent
    }
}

@InstallIn(DomainCustomComponent::class)
@EntryPoint
internal interface DomainCustomComponentEntryPoint {
    @CustomComponentBridge
    fun domainRepository(): DomainRepository
    @CustomComponentBridge
    fun domainColor(): ColorDefinition.DomainColor
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
@InstallIn(SingletonComponent::class)
internal object DomainCustomComponentBridge {
    @Provides
    internal fun provideDomainRepository(
        componentManager: DomainCustomComponentManager
    ): DomainRepository {
        return EntryPoints.get(componentManager, DomainCustomComponentEntryPoint::class.java)
            .domainRepository()
    }

    @Provides
    internal fun provideDomainColor(
        componentManager: DomainCustomComponentManager
    ): ColorDefinition.DomainColor {
        return EntryPoints.get(componentManager, DomainCustomComponentEntryPoint::class.java)
            .domainColor()
    }
}