package com.enginebai.poc.data

import kotlin.random.Random

interface DomainRepository {
    fun getDataList(): List<String>
    fun addData(domainData: DomainData)
    fun addRandomData()
}

class DomainRepositoryImpl(
    private val api: DomainApi
) : DomainRepository {
    override fun getDataList(): List<String> {
        return api.getDataList().map { it.domainTopic.type }
    }

    override fun addData(domainData: DomainData) {
        api.addData(domainData)
    }

    override fun addRandomData() {
        val topics = DomainTopic.values()
        api.addData(DomainData(topics[Random.nextInt(0, topics.size)]))
    }
}

