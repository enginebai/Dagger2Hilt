package com.enginebai.poc.di

import com.enginebai.poc.data.user.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import kotlin.random.Random
import kotlin.random.nextInt

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
    @Provides
    fun provideUser(): User {
        return User(
            id = "1234",
            name = "Test",
            age = Random.nextInt(0..100000)
        )
    }
}