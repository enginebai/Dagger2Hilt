package com.enginebai.core.data

import javax.inject.Inject
import kotlin.random.Random

private const val CARD_COUNT = 5

class CardManager @Inject constructor() {

    fun shuffle(): Array<Card> {
        val suits = Suit.values()
        return Array(CARD_COUNT) {
            Card(suits[Random.nextInt(suits.size)],
            Random.nextInt(1, 13))
        }
    }
}