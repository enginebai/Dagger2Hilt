package com.enginebai.poc.data

enum class DomainTopic(val type: String) {
    DYNAMIC_PROGRAMMING("Dynamic Programming"),
    DFS("Depth First Search"),
    BFS("Breadth First Search"),
    BINARY_SEARCH("Binary Search"),
    BACKTRACKING("Backtracking"),
    ARRAY("Array"),
    TWO_POINTERS("Two Pointers"),
    SLIDING_WINDOW("Sliding Window"),
    TREE("TREE"),
    LINKED_LIST("Linked List"),
}

data class DomainData(
    val domainTopic: DomainTopic
)