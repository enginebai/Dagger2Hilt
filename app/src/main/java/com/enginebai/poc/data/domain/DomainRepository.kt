package com.enginebai.poc.data

import com.enginebai.poc.data.domain.DomainData
import com.enginebai.poc.data.domain.pickRandomTopic

interface DomainRepository {
    fun getDataList(): List<String>
    fun addData(domainData: DomainData)
    fun addRandomData()
}

class DomainRepositoryImpl(
    private val api: DomainApi
) : DomainRepository {
    override fun getDataList(): List<String> {
        return api.getDataList().map { it.domainTopic.courseName }
    }

    override fun addData(domainData: DomainData) {
        api.addData(domainData)
    }

    override fun addRandomData() {
        api.addData(DomainData(pickRandomTopic()))
    }
}

