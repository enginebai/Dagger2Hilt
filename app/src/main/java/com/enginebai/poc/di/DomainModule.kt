package com.enginebai.poc.di

import com.enginebai.core.di.DomainCustomComponent
import com.enginebai.core.di.DomainScope
import com.enginebai.poc.data.*
import com.enginebai.poc.data.domain.*
import com.enginebai.poc.data.user.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import kotlin.random.Random

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