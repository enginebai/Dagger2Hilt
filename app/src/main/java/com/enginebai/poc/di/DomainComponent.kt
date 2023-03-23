package com.enginebai.poc.di

import com.enginebai.core.di.DomainColorModule
import com.enginebai.core.di.DomainScope
import com.enginebai.poc.MyApplication
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.ui.widget.RandomTopicButton
import dagger.Module
import dagger.Subcomponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(includes = [
    DomainModule::class,
    ApiModule::class,
    DomainColorModule::class,
    ActivityBuilderModule::class,
    FragmentBuilderModule::class]
)
interface DomainAggregatorModule

@InstallIn(SingletonComponent::class)
@EntryPoint
interface DomainComponent : DomainAggregatorModule {

    /*
     * add some dependencies binding methods: A getA();
     * 1. interface
     * 2. dagger provider itself or injector
     */
    fun domainRepository(): DomainRepository
}