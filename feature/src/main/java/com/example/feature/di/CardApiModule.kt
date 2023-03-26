package com.example.feature.di

import com.enginebai.core.card.Poker
import com.enginebai.core.di.DomainScope
import com.example.feature.data.CardApi
import dagger.Module
import dagger.Provides

@Module
class CardApiModule {
    @Provides
    @DomainScope
    internal fun provideCardApi(
        poker: Poker
    ): CardApi {
        return CardApi(poker)
    }
}