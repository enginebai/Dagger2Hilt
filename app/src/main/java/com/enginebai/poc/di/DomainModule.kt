package com.enginebai.poc.di

import com.enginebai.core.di.CustomComponentBridge
import com.enginebai.core.di.DomainCustomComponent
import com.enginebai.core.di.DomainScope
import com.enginebai.core.util.ColorDefinition
import com.enginebai.core.util.ColorManager
import com.enginebai.poc.data.*
import com.enginebai.poc.data.domain.*
import com.enginebai.poc.data.user.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import kotlin.random.Random
import kotlin.random.nextInt

@Module
@InstallIn(DomainCustomComponent::class)
class DomainModule {
    @Provides
    @DomainScope
    fun provideDefaultDomainType(): DomainTopic {
        return pickRandomTopic()
    }

    @Provides
    @DomainScope
    @CustomComponentBridge
    fun provideDomainRepository(api: DomainApi): DomainRepository {
        return DomainRepositoryImpl(api)
    }

    @Provides
    @DomainScope
    @CustomComponentBridge
    fun provideDomainUser(): User {
        val n = Random.nextInt(10000)
        return User("$n", "Domain User $n", n)
    }

    @Provides
    @DomainScope
    @CustomComponentBridge
    fun provideDomainColor(): ColorDefinition.DomainColor {
        return ColorDefinition.DomainColor(ColorManager.generateColor())
    }
}