package com.enginebai.poc.di

import com.enginebai.core.di.BridgeScope
import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.domain.DomainRepository
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
    fun domainRepository(): DomainRepository
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

//@Module
//@InstallIn(SingletonComponent::class)
//internal object DomainCustomComponentBridge {
//
//    @BridgeScope
//    @Provides
//    internal fun provideDomainRepositoryFromBridge(
//        componentManager: DomainCustomComponentManager
//    ): DomainRepository {
//        return EntryPoints.get(componentManager, DomainCustomComponentEntryPoint::class.java)
//            .domainRepository()
//    }
//}