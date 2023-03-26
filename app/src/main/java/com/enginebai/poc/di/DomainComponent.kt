package com.enginebai.poc.di

import com.enginebai.core.di.DomainColorModule
import com.enginebai.core.util.ColorDefinition
import com.enginebai.poc.data.domain.DomainRepository
import com.enginebai.poc.data.domain.DomainTopic
import com.enginebai.poc.ui.widget.RandomTopicItem
import com.example.feature.data.CardRepository
import com.example.feature.di.CardApiModule
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(includes = [
    DomainModule::class,
    ApiModule::class,
    DomainColorModule::class,
    CardApiModule::class]
)
interface DomainAggregatorModule

@InstallIn(SingletonComponent::class)
@EntryPoint
interface DomainComponent : DomainAggregatorModule {
    
    fun inject(nonAndroidClass: RandomTopicItem)

    /*
     * add some dependencies binding methods: A getA();
     * 1. interface
     * 2. dagger provider itself or injector
     */
    fun domainRepository(): DomainRepository

    // Provide to dynamic feature
    fun domainTopic(): DomainTopic
    fun cardRepository(): CardRepository
    fun domainColor(): ColorDefinition.DomainColor
}