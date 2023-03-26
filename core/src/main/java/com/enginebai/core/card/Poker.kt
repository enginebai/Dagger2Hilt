package com.enginebai.core.card

interface Poker {
    fun getRandomSuit(): Suit
    @androidx.annotation.IntRange(from = 1, to = 13) fun getRandomNumber(): Int
}