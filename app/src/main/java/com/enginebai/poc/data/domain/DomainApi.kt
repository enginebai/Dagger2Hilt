package com.enginebai.poc.data

import com.enginebai.poc.data.domain.DomainData
import com.enginebai.poc.data.domain.DomainTopic

interface DomainApi {
    fun getDataList(): List<DomainData>
    fun addData(domainData: DomainData)
}

class DomainApiInMemory(
    private val defaultType: DomainTopic
) : DomainApi {

    private val list = mutableListOf<DomainData>()

    init {
        list.add(DomainData(defaultType))
    }

    override fun getDataList(): List<DomainData> {
        return list
    }

    override fun addData(domainData: DomainData) {
        list.add(domainData)
    }
}