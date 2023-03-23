package com.enginebai.poc.di.custom

import com.enginebai.core.di.DomainCustomComponent
import dagger.hilt.internal.GeneratedComponentManager
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class DomainCustomComponentManager @Inject constructor(
    private val componentProvider: Provider<DomainCustomComponent.Builder>
) : GeneratedComponentManager<DomainCustomComponent> {

    @Volatile
    private var component: DomainCustomComponent = generateComponent()

    override fun generatedComponent(): DomainCustomComponent = component

    fun regenerateComponent() {
        component = generateComponent()
    }

    @Synchronized private fun generateComponent(): DomainCustomComponent {
        return componentProvider.get().build()
    }
}