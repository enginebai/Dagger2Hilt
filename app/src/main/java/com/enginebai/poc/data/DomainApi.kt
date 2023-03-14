package com.enginebai.poc.data

interface DomainApi {
    fun getDataList(): List<DomainData>
    fun addData(domainData: DomainData)
}

class DomainApiInMemory(
    private val defaultType: DomainType
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