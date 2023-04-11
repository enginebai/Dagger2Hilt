package com.enginebai.core.di

import com.enginebai.core.util.ColorDefinition
import com.enginebai.core.util.ColorManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainColorModule {
    @Provides
    @DomainScope
    fun provideDomainColor(): ColorDefinition.DomainColor {
        return ColorDefinition.DomainColor(ColorManager.generateColor())
    }
}