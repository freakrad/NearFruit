package com.example.nearfruit.utils

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin
import kotlin.math.sqrt

object HitungJarak {

    //Rumus Jarak Haversine
    fun getDistance(
        currentLatitude: Double,
        currentLongitude: Double,
        destinationLatitude: Double,
        destinationLongitude: Double,
    ): Double {
        val earthRadius = 3958.75
        val dLatitude = Math.toRadians(destinationLatitude - currentLatitude)
        val dLongitude = Math.toRadians(destinationLongitude - currentLongitude)
        val a = sin(dLatitude / 2) * sin(dLatitude / 2) +
                cos(Math.toRadians(currentLatitude)) *
                cos(Math.toRadians(destinationLatitude)) *
                sin(dLongitude / 2) * sin(dLongitude / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val dDistance = earthRadius * c
        val meterConversion = 1609
        val myDistance = dDistance * meterConversion
        return floor(myDistance / 1000)
    }
}