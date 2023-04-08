package com.enginebai.dynamic.di

import com.enginebai.poc.di.koin.KoinModulesProvider
import org.koin.core.module.Module

class DynamicFeatureKoinModulesProvider : KoinModulesProvider {
    override fun generateModules(): List<Module> {
        // TODO: provide the modules we need in this module
        return listOf()
    }
}