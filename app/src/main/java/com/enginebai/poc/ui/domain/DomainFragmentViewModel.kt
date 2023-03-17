package com.enginebai.poc.ui.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enginebai.poc.data.DomainRepository
import javax.inject.Inject

class DomainFragmentViewModel @Inject constructor(
    private val domainRepository: DomainRepository
) : ViewModel() {

    private val _data = MutableLiveData<String>()
    val data: LiveData<String> = _data

    init {
        refreshData()
    }

    fun addData() {
        domainRepository.addRandomTopic()
        refreshData()
    }

    private fun refreshData() {
        _data.value = domainRepository.getDataList().joinToString("\n")
    }
}