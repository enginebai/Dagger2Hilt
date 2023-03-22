package com.enginebai.core.util

import android.graphics.Color
import androidx.annotation.ColorInt
import kotlin.random.Random
import kotlin.random.nextInt

data class RGB(
    @androidx.annotation.IntRange(from = 0, to = 255) var red: Int,
    @androidx.annotation.IntRange(from = 0, to = 255) var green: Int,
    @androidx.annotation.IntRange(from = 0, to = 255) var blue: Int
) {
    @ColorInt fun toColor(): Int {
        return Color.rgb(
            this.red,
            this.green,
            this.blue
        )
    }
}

abstract class ColorDefinition {

    abstract var color: RGB
    data class AppColor(override var color: RGB): ColorDefinition()
    data class SingletonColor(override var color: RGB): ColorDefinition()
    data class DomainColor(override var color: RGB): ColorDefinition()
}

object ColorManager {
    fun generateColor(): RGB {
        return RGB(
            Random.nextInt(0..255),
            Random.nextInt(0,255),
            Random.nextInt(0..255)
        )
    }

    fun generateColors(): List<RGB> {
        val list = mutableListOf<RGB>()
        for (i in 1..10) list.add(generateColor())
        return list
    }
}