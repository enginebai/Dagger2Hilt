package com.enginebai.dynamic.di

import com.enginebai.core.ListNode
import com.enginebai.core.card.Card
import com.enginebai.core.card.Poker
import dagger.Module
import dagger.Provides

@Module
class DynamicFeatureModule {
    @Provides
    fun provideCardLinkedList(poker: Poker): ListNode<Card> {
        val head = ListNode(Card(poker.getRandomSuit(), poker.getRandomNumber()))
        head.next = ListNode(Card(poker.getRandomSuit(), poker.getRandomNumber()))
        return head
    }
}