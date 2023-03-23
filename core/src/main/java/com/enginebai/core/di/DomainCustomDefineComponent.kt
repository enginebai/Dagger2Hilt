package com.enginebai.core.di

import dagger.hilt.DefineComponent
import dagger.hilt.components.SingletonComponent

@DomainScope
@DefineComponent(parent = SingletonComponent::class)
interface DomainCustomDefineComponent {
    @DefineComponent.Builder
    interface Builder {
        fun build(): DomainCustomDefineComponent
    }
}