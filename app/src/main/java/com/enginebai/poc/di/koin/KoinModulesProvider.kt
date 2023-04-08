package com.enginebai.poc.di.koin

import org.koin.core.module.Module

interface KoinModulesProvider {
    fun generateModules(): List<Module>
}
