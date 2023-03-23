package com.enginebai.poc.di.custom

import com.enginebai.core.di.DomainCustomDefineComponent
import dagger.hilt.internal.GeneratedComponentManager
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class DomainCustomComponentManager @Inject constructor(
    private val componentProvider: Provider<DomainCustomDefineComponent.Builder>
) : GeneratedComponentManager<DomainCustomDefineComponent> {

    @Volatile
    private var component: DomainCustomDefineComponent = generateComponent()

    override fun generatedComponent(): DomainCustomDefineComponent = component

    fun regenerateComponent() {
        component = generateComponent()
    }

    @Synchronized private fun generateComponent(): DomainCustomDefineComponent {
        return componentProvider.get().build()
    }
}