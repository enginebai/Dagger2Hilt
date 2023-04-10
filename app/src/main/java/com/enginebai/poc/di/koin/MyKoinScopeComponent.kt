package com.enginebai.poc.di.koin

import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import kotlin.random.Random

data class DomainInstance(val number: Int = Random.nextInt(10000))

val myScopeName = named("Domain")

class MyKoinScopeComponent : KoinScopeComponent {
    override val scope: Scope by lazy { createScope(myScopeName) }

    fun close() {
        scope.close()
    }
}
