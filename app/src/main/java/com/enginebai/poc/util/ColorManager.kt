package com.enginebai.poc.util

import android.graphics.Color
import androidx.annotation.ColorInt
import kotlin.random.Random
import kotlin.random.nextInt

object ColorManager {
    @ColorInt fun generateColor(): Int {
        return Color.argb(
            255,
            Random.nextInt(0..255),
            Random.nextInt(0..255),
            Random.nextInt(0..255)
        )
    }
}