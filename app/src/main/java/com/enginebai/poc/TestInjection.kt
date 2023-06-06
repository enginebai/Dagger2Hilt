package com.enginebai.poc

import android.util.Log
import com.enginebai.core.base.BaseViewModel
import com.enginebai.core.card.Poker
import com.enginebai.core.util.ColorDefinition
import com.enginebai.ksp.Yo
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.user.UserDataHelper
import com.enginebai.poc.di.koin.DIStringType
import com.enginebai.poc.util.ColorMixer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import java.util.Calendar
import java.util.UUID
import kotlin.properties.Delegates

@Yo
class AClass(private val a: Int, val b: String, val c: Double) {
    val p = "$a, $b, $c"
    fun foo() = p
}

class Test constructor(
    userDataHelper: UserDataHelper,
    private val poker: Poker,
    var name: String
) : KoinComponent {
    // NOTE: KSP can't tell if the property is initialized by delegation.
    // Source: https://github.com/google/ksp/issues/1249
    private val uuid: UUID by inject()
    private val label: String by inject(named(DIStringType.DEBUG))
    private val appColor by inject<ColorDefinition.AppColor>()
    private val count by Delegates.observable(0) { _, old, new ->
        Log.i("$this", "old: $old, new: $new")
    }

    // TODO: get() eager fetch
    // TODO: Companion object : KoinComponent

    // NOTE: KSP does not support parsing function body.
    // Source: https://github.com/google/ksp/issues/1379
}

class Test2(
    val test: DomainRepository, test2: Calendar,
    private val test3: ColorMixer
) : BaseViewModel(), KoinComponent {

}

// TODO: Object : KoinComponent
// TODO: Top-level function that gets types from koin
// TODO: Extension function that gets types from koin