package com.enginebai.core.di

import dagger.hilt.DefineComponent
import dagger.hilt.components.SingletonComponent

@DomainScope
@DefineComponent(parent = SingletonComponent::class)
interface DomainCustomComponent {
    @DefineComponent.Builder
    interface Builder {
        fun build(): DomainCustomComponent
    }
}