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

@Yo
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

    init {
        val a = AClassBuilder()
            .withA(1)
            .withB("2")
            .withC(3.0)
            .build()
        a.foo()
    }
}

class Test2(val test: DomainRepository, test2: Calendar,
            private val test3: ColorMixer
) : BaseViewModel(), KoinComponent {
    private val appColor by inject<ColorDefinition.AppColor>()
}