package com.enginebai.poc.data

import com.enginebai.poc.data.domain.DomainData
import com.enginebai.poc.data.domain.DomainTopic
import com.enginebai.poc.data.domain.pickRandomTopic

interface DomainRepository {
    fun getDataList(): List<String>
    fun addTopic(topic: DomainTopic)
    fun addRandomTopic()
}

class DomainRepositoryImpl(
    private val api: DomainApi
) : DomainRepository {
    override fun getDataList(): List<String> {
        return api.getDataList().map { it.domainTopic.courseName }
    }

    override fun addTopic(topic: DomainTopic) {
        api.addData(DomainData(topic))
    }

    override fun addRandomTopic() {
        api.addData(DomainData(pickRandomTopic()))
    }
}

