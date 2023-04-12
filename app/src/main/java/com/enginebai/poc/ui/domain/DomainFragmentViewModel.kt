package com.enginebai.poc.ui.domain

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.enginebai.core.base.BaseViewModel
import com.enginebai.poc.ComplexInjection
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.user.User
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class DomainFragmentUser(
    val user: User
) : Parcelable

class DomainFragmentViewModel @Inject constructor(
    private val domainRepository: DomainRepository,
    state: SavedStateHandle,
    private val complexInjection: ComplexInjection
) : BaseViewModel() {

    private val _data = MutableLiveData<String>()
    val data: LiveData<String> = _data

    init {
        val domainFragmentUser: DomainFragmentUser? = state.get<DomainFragmentUser>(KEY_DOMAIN_USER)
        _data.value = "Injected domain user $domainFragmentUser"
        complexInjection.log()
    }

    fun addData() {
        domainRepository.addRandomTopic()
        refreshData()
        complexInjection.log()
    }

    private fun refreshData() {
        _data.value = domainRepository.getDataList().joinToString("\n")
    }
}