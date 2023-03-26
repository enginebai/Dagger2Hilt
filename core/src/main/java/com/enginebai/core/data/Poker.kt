package com.enginebai.core.data

import javax.inject.Inject
import kotlin.random.Random

class Poker @Inject constructor() {

    fun getRandomSuit(): Suit {
        val suits = Suit.values()
        return suits[Random.nextInt(suits.size)]
    }

    @androidx.annotation.IntRange(from = 1, to = 13) fun getRandomNumber(): Int {
        return Random.nextInt(1, 13)
    }
}