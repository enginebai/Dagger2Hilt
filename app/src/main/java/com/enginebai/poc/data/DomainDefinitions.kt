package com.enginebai.poc.data

enum class DomainType(val type: String) {
    DYNAMIC_PROGRAMMING("Dynamic Programming"),
    DFS("Depth First Search"),
    BFS("Breadth First Search")
}

data class DomainData(
    val domainType: DomainType
)