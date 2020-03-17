package com.example.nasa_nws_dk.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AsteroidResponse(
    @Json(name = "near_earth_objects")
    val nearEarthObjects: Map<String, List<Asteroid>>
) : Parcelable

fun AsteroidResponse.asDatabaseModel(): Array<Asteroid> {
    return nearEarthObjects.flatMap {
        it.value
    }.map {
        Asteroid(
            id = it.id,
            name = it.name,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}