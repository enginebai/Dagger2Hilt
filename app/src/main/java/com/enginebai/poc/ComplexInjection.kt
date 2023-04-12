package com.enginebai.poc

import android.util.Log
import com.enginebai.core.card.Poker
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.user.UserDataHelper
import com.example.feature.data.CardRepository
import javax.inject.Inject

class ComplexInjection(
    private val username: String,
    private val poker: Poker,
    private val userDataHelper: UserDataHelper,
    private val domainRepository: DomainRepository,
    private val cardRepository: CardRepository,
) {
    fun log() {
        Log.i(
            ComplexInjection::class.java.simpleName, """
            Username = $username\n
            ${poker::class.java.simpleName} = ${poker.getRandomSuit().symbol}${poker.getRandomNumber()}
            ${userDataHelper::class.java.simpleName} = ${userDataHelper.getUser()}
            ${domainRepository::class.java.simpleName} = ${domainRepository.getDataList()}
            ${cardRepository::class.java.simpleName} = ${cardRepository.getCards().map { "${it.suit.symbol}${it.number}" }}
        """.trimIndent())
    }
}