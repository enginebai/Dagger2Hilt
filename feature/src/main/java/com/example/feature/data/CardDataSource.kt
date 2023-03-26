package com.example.feature.data

import com.enginebai.core.card.Card
import com.enginebai.core.card.Poker
import javax.inject.Inject

class CardApi @Inject constructor(
    private val poker: Poker
) {
    fun getCard(): Card {
        return Card(poker.getRandomSuit(), poker.getRandomNumber())
    }
}

interface CardRepository {
    fun getCards(): Array<Card>
}

class CardRepositoryImpl @Inject constructor(
    private val api: CardApi
) : CardRepository {

    private var cards: Array<Card> = Array(5) {
        api.getCard()
    }

    override fun getCards(): Array<Card> {
        return cards
    }
}