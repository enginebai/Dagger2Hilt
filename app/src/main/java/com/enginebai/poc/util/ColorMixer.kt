package com.enginebai.poc.util

import com.enginebai.core.util.ColorDefinition
import com.enginebai.core.util.ColorManager
import com.enginebai.core.util.RGB
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import javax.inject.Inject

// NOTE: To inject from constructor or field to non-android class, we create the instance from Dagger.
class ColorMixer @Inject constructor() : KoinComponent {

    private val singletonColor: ColorDefinition.SingletonColor by inject()

    fun mixColor(): List<ColorDefinition.AppColor> {
        val appColors = get<List<ColorDefinition.AppColor>>()
        val mix = ColorManager.generateColor()
        return appColors.map {
            it.copy(
                RGB(
                    it.color.red + mix.red,
                    it.color.green + singletonColor.color.red + mix.green,
                    it.color.blue + singletonColor.color.red + mix.blue
                )
            )
        }
    }

}