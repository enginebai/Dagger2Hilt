package com.enginebai.poc.data.domain

import kotlin.random.Random

fun pickRandomTopic(): DomainTopic {
    val values = DomainTopic.values()
    return values[Random.nextInt(0, values.size)]
}

enum class DomainTopic(val courseName: String) {
    DYNAMIC_PROGRAMMING("Dynamic Programming"),
    DFS("Depth First Search"),
    BFS("Breadth First Search"),
    BINARY_SEARCH("Binary Search"),
    BACKTRACKING("Backtracking"),
    ARRAY("Array"),
    TWO_POINTERS("Two Pointers"),
    SLIDING_WINDOW("Sliding Window"),
    TREE("Tree"),
    LINKED_LIST("Linked List"),
    Stack("Stack"),
    Queue("Queue");
}

data class DomainData(
    val domainTopic: DomainTopic
)