package com.example.feature.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enginebai.core.base.BaseViewModel
import com.example.feature.data.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: CardRepository
) : BaseViewModel() {

    private val _card = MutableLiveData<String>()
    val card: LiveData<String> = _card

    fun play() {
        _card.value = repository
            .getCards()
            .joinToString("\n") { "${it.suit.symbol}${it.number}" }
    }
}