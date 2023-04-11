package com.enginebai.dynamic.di

import com.enginebai.core.ListNode
import com.enginebai.core.card.Card
import com.enginebai.core.card.Poker
import com.enginebai.dynamic.DynamicInstance
import com.enginebai.poc.di.koin.KoinModulesProvider
import org.koin.core.module.Module
import org.koin.dsl.module

// NOTE: Use reflection to create the instance of this class
class DynamicFeatureKoinModulesProvider : KoinModulesProvider {
    override fun generateModules(): List<Module> {
        return listOf(module {
            single { DynamicInstance("Porsche", 6666) }
            factory {
                val poker = get<Poker>()
                val head = ListNode(Card(poker.getRandomSuit(), poker.getRandomNumber()))
                head.next = ListNode(Card(poker.getRandomSuit(), poker.getRandomNumber()))
                head
            }
        })
    }
}