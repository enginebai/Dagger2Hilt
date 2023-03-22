package com.enginebai.core.di

import com.enginebai.core.util.ColorDefinition
import com.enginebai.core.util.ColorManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppColorModule {
    @Provides
    @Singleton
    fun provideAppColor(): List<ColorDefinition.AppColor> {
        return ColorManager.generateColors().map { ColorDefinition.AppColor(it) }
    }

    @Provides
    @Singleton
    fun provideSingletonColor(): ColorDefinition.SingletonColor {
        return ColorDefinition.SingletonColor(ColorManager.generateColor())
    }
}

@Module
class DomainColorModule {
    @Provides
    @DomainScope
    fun provideDomainColor(): ColorDefinition.DomainColor {
        return ColorDefinition.DomainColor(ColorManager.generateColor())
    }
}