package com.enginebai.poc.ui.domain

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enginebai.core.base.BaseViewModel
import com.enginebai.poc.data.domain.DomainRepository
import com.enginebai.poc.data.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class DomainFragmentUser(
    val user: User
) : Parcelable

@HiltViewModel
class DomainFragmentViewModel @Inject constructor(
    private val domainRepository: DomainRepository
) : BaseViewModel() {

    private val _data = MutableLiveData<String>()
    val data: LiveData<String> = _data

    fun setDomainFragmentUser(domainFragmentUser: DomainFragmentUser) {
        _data.value = "Injected domain user $domainFragmentUser"
    }

    fun addData() {
        domainRepository.addRandomTopic()
        refreshData()
    }

    private fun refreshData() {
        _data.value = domainRepository.getDataList().joinToString("\n")
    }
}