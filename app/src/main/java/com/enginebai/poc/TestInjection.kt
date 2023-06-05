package com.enginebai.poc

import com.enginebai.core.base.BaseViewModel
import com.enginebai.core.card.Poker
import com.enginebai.core.util.ColorDefinition
import com.enginebai.ksp.Yo
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.user.UserDataHelper
import com.enginebai.poc.util.ColorMixer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar
import java.util.UUID

class AClass(private val a: Int, val b: String, val c: Double) {
    val p = "$a, $b, $c"
    fun foo() = p
}

class Test constructor(
    userDataHelper: UserDataHelper,
    private val poker: Poker,
    var name: String
) : BaseViewModel(), KoinComponent {
    private val uuid: UUID by inject()

    // TODO: get() eager fetch
    // TODO: Companion object : KoinComponent
}

class Test2(val test: DomainRepository, test2: Calendar,
            private val test3: ColorMixer
) : BaseViewModel(), KoinComponent {
    private val appColor by inject<ColorDefinition.AppColor>()
}

// TODO: Object : KoinComponent
// TODO: Top-level function that gets types from koin
// TODO: Extension function that gets types from koin