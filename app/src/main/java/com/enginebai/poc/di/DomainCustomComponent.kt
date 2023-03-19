package com.enginebai.poc.di

import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.DomainRepository
import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
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
        fun setDomainModule(module: DomainModule): Builder
        fun setApiModule(module: ApiModule): Builder
        fun build(): DomainCustomComponent
    }
}

@InstallIn(DomainCustomComponent::class)
@EntryPoint
interface DomainCustomComponentEntryPoint {
    fun domainRepository(): DomainRepository
}

@Singleton
class DomainCustomComponentManager @Inject constructor(
    private val componentProvider: Provider<DomainCustomComponent.Builder>
): GeneratedComponentManager<DomainCustomComponent> {

    private var component: DomainCustomComponent = componentProvider.get().build()

    override fun generatedComponent(): DomainCustomComponent = component

    fun regenerateComponent() {
        component = componentProvider.get().build()
    }
}