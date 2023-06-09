package com.enginebai.poc.util

import android.content.Context
import com.enginebai.core.util.ColorDefinition
import com.enginebai.core.util.ColorManager
import com.enginebai.core.util.RGB
import com.enginebai.poc.MyApplication
import javax.inject.Inject

// NOTE: To inject from constructor or field to non-android class, we create the instance from Dagger.
class ColorMixer @Inject constructor() {

    @Inject
    lateinit var singletonColor: ColorDefinition.SingletonColor

    fun mixColor(context: Context): List<ColorDefinition.AppColor> {
        val appComponent = (context.applicationContext as MyApplication).appComponent()
        val appColors = appComponent.appColors()
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