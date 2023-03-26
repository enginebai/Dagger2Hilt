package com.example.feature.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enginebai.core.base.BaseViewModel
import com.example.feature.data.CardRepository
import javax.inject.Inject

class CardViewModel @Inject constructor(
    private val repository: CardRepository
) : BaseViewModel() {

    private val _card = MutableLiveData<String>()
    val card: LiveData<String> = _card

    fun play() {
        _card.value = repository
            .startNewGame()
            .joinToString("\n") { "${it.suit}-${it.number}" }
    }
}