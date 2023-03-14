package com.enginebai.poc.data

interface DomainRepository {
    fun getDataList(): List<DomainData>
    fun addData(domainData: DomainData)
}

class DomainRepositoryImpl(
    private val api: DomainApi
) : DomainRepository {
    override fun getDataList(): List<DomainData> {
        return api.getDataList()
    }

    override fun addData(domainData: DomainData) {
        api.addData(domainData)
    }
}

