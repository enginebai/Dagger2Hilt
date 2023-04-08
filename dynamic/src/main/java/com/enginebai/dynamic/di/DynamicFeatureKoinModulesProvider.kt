package com.enginebai.dynamic.di

import com.enginebai.dynamic.DynamicInstance
import com.enginebai.poc.di.koin.KoinModulesProvider
import org.koin.core.module.Module
import org.koin.dsl.module

// NOTE: Use reflection to create the instance of this class
class DynamicFeatureKoinModulesProvider : KoinModulesProvider {
    override fun generateModules(): List<Module> {
        return listOf(module {
            single { DynamicInstance("Porsche", 6666) }
        })
    }
}