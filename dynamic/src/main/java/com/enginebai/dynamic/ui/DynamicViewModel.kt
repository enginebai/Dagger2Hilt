package com.enginebai.dynamic.ui

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
import javax.inject.Inject
import kotlin.random.Random

class DynamicViewModel @Inject constructor(
    singletonColor: ColorDefinition.SingletonColor,
    private val randomNumber: Int,
    private val domainRepository: DomainRepository,
    private val domainTopic: DomainTopic,
    cardListHead: ListNode<Card>,
    private val cardRepository: CardRepository,
): BaseViewModel() {

    private val _textColor = MutableLiveData<Int>()
    val textColor: LiveData<Int> = _textColor

    private val _cardList = MutableLiveData<ListNode<Card>>()
    val cardList: LiveData<ListNode<Card>> = _cardList

    init {
        _textColor.value = singletonColor.color.toColor()
        _cardList.value = cardListHead
    }

    fun appendTopicToHead() {
        val newNode = getNewCardNode()
        newNode.next = _cardList.value
        _cardList.value = newNode
    }

    fun appendTopicToRear() {
        val newNode = getNewCardNode()
        var lastNode = _cardList.value
        if (lastNode == null) {
            _cardList.value = newNode
            return
        }
        while (lastNode?.next != null) {
            lastNode = lastNode.next
        }
        lastNode?.next = newNode
    }

    private fun getNewCardNode(): ListNode<Card> {
        val newCards = cardRepository.getCards()
        return ListNode(newCards[Random.nextInt(newCards.size)])
    }
}