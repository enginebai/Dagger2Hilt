package com.enginebai.poc.ui.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enginebai.core.base.BaseViewModel
import com.enginebai.poc.data.DomainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DomainFragmentViewModel @Inject constructor(
    private val domainRepository: DomainRepository
) : BaseViewModel() {

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