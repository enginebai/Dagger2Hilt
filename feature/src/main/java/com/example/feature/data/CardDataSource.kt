package com.example.feature.data

import com.enginebai.core.data.Card
import com.enginebai.core.data.Poker
import javax.inject.Inject

// TODO: Singleton in ApiModule
class CardApi @Inject constructor(
    private val poker: Poker
) {
    fun getCard(): Card {
        return Card(poker.getRandomSuit(), poker.getRandomNumber())
    }
}

// TODO: Domain 
interface CardRepository {
    fun startNewGame(): Array<Card>
}

class CardRepositoryImpl @Inject constructor(
    val api: CardApi
) : CardRepository {
    override fun startNewGame(): Array<Card> {
        TODO("Not yet implemented")
    }

}