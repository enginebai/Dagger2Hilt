package com.enginebai.core.data

enum class Suit(val symbol: String) {
    SPADE("♠️"),
    HEART("♥️"),
    DIAMOND("♦️"),
    CLUB("♣️")
}

data class Card(val suit: Suit, @androidx.annotation.IntRange(from = 1, to = 13) val number: Int)