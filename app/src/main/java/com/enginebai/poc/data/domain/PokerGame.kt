package com.enginebai.poc.data.domain

import com.enginebai.core.card.Poker
import com.enginebai.core.card.Suit
import javax.inject.Inject
import kotlin.random.Random

class PokerGame @Inject constructor() : Poker {

    override fun getRandomSuit(): Suit {
        val suits = Suit.values()
        return suits[Random.nextInt(suits.size)]
    }

    @androidx.annotation.IntRange(from = 1, to = 13)
    override fun getRandomNumber(): Int {
        return Random.nextInt(1, 13)
    }
}