package com.enginebai.dynamic.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enginebai.core.ListNode
import com.enginebai.core.base.BaseViewModel
import com.enginebai.core.card.Card
import com.enginebai.core.card.Suit
import com.enginebai.core.util.ColorDefinition
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.domain.DomainTopic
import com.example.feature.data.CardRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import javax.inject.Inject
import kotlin.random.Random

class DynamicViewModel(
    randomNumber: Int,
    domainRepository: DomainRepository,
    domainTopic: DomainTopic,
    private var cardListHead: ListNode<Card>,
    private val cardRepository: CardRepository,
): BaseViewModel(), KoinComponent {

    private val _textColor = MutableLiveData<Int>()
    val textColor: LiveData<Int> = _textColor

    private val _cardList = MutableLiveData<String>()
    val cardList: LiveData<String> = _cardList

    private val singletonColor: ColorDefinition.SingletonColor by inject()

    init {
        Log.d(
            this.javaClass.simpleName, """
            Random Number = $randomNumber
            Domain Repository = ${domainRepository.getDataList()}
            Domain Topic = ${domainTopic.courseName}
        """.trimIndent())
        _textColor.value = singletonColor.color.toColor()
        refreshCardList()
    }

    fun appendTopicToHead() {
        val newNode = getNewCardNode()
        newNode.next = cardListHead
        cardListHead = newNode
        refreshCardList()
    }

    fun appendTopicToRear() {
        val newNode = getNewCardNode()
        var lastNode: ListNode<Card>? = cardListHead
        if (lastNode == null) {
            cardListHead = newNode
            return
        }
        while (lastNode?.next != null) {
            lastNode = lastNode.next
        }
        lastNode?.next = newNode
        refreshCardList()
    }

    private fun getNewCardNode(): ListNode<Card> {
        val newCards = cardRepository.getCards()
        return ListNode(newCards[Random.nextInt(newCards.size)])
    }

    private fun refreshCardList() {
        var node: ListNode<Card>? = cardListHead
        val str = StringBuilder()
        while (node != null) {
            str.append("${node.data.suit.symbol} ${node.data.number}")
            node = node.next
            if (node != null)
                str.append(" -> ")
        }
        _cardList.value = str.toString()
    }
}