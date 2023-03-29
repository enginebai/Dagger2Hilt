package com.enginebai.dynamic.di

import com.enginebai.core.ListNode
import com.enginebai.core.card.Card
import com.enginebai.core.card.Poker
import com.enginebai.core.di.MyActivityScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class DynamicFeatureModule {
    @Provides
    fun provideCardLinkedList(poker: Poker): ListNode<Card> {
        val head = ListNode(Card(poker.getRandomSuit(), poker.getRandomNumber()))
        head.next = ListNode(Card(poker.getRandomSuit(), poker.getRandomNumber()))
        return head
    }
}