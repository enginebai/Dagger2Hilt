package com.enginebai.poc.data.user

import android.content.Context
import com.enginebai.poc.MyApplication

private const val HEIGHT_MAX_VALUE = 1000
private const val WEIGHT_MAX_VALUE = 8888
private const val WEIGHT_MIN_VALUE = 0

data class User(
    val id: String,
    var name: String,
    var age: Int,
    val body: Body)

data class Body(
    var height: Int,
    var weight: Int
) {
    fun becomeTallest() {
        height = HEIGHT_MAX_VALUE
    }

    fun becomeShortest() {
        height = -HEIGHT_MAX_VALUE
    }

    fun gainHeight() {
        height += 50
    }

    fun loseHeight() {
        height -= 50
    }

    fun gainWeight() {
        weight += 100
    }

    fun loseWeight() {
        weight -= 100
    }

    fun becomeMostWeighted() {
        weight = WEIGHT_MAX_VALUE
    }

    fun noGravity() {
        weight = WEIGHT_MIN_VALUE
    }

    fun isStrongest() = height == HEIGHT_MAX_VALUE && weight == WEIGHT_MAX_VALUE

    override fun toString(): String {
        return "H: $height\nW: $weight"
    }
}

object WorkoutCoach {
    fun train(context: Context) {
        val body = (context.applicationContext as MyApplication).component().userDataHelper().getUser().body
        for (i in 1..10) {
            body.gainHeight()
            body.gainWeight()
        }
    }
}