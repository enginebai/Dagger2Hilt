package com.enginebai.poc.di

import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.*
import com.enginebai.poc.data.domain.DomainTopic
import com.enginebai.poc.data.domain.pickRandomTopic
import com.enginebai.core.util.ColorDefinition
import com.enginebai.core.util.ColorManager
import com.enginebai.poc.data.user.User
import dagger.Module
import dagger.Provides
import kotlin.random.Random

@Module
class DomainModule {
    @Provides
    @DomainScope
    fun provideDefaultDomainType(): DomainTopic {
        return pickRandomTopic()
    }

    @Provides
    @DomainScope
    fun provideDomainRepository(api: DomainApi): DomainRepository {
        return DomainRepositoryImpl(api)
    }

    @Provides
    @DomainScope
    fun provideDomainUser(): User {
        val n = Random.nextInt(10000)
        return User("$n", "Domain User $n", n)
    }
}