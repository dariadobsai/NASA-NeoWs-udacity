package com.example.nasa_nws_dk.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Asteroid(
    @PrimaryKey
    val id: Long,
    val name: String,
    @Json(name = "close_approach_data")
    val closeApproachDate: List<CloseApproachData>,
    @Json(name = "absolute_magnitude_h")
    val absoluteMagnitude: Double,
    @Json(name = "estimated_diameter")
    val estimatedDiameter: EstimatedDiameter,
    @Json(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardous: Boolean
) : Parcelable

@Parcelize
data class EstimatedDiameter(val kilometers: Kilometers) : Parcelable

@Parcelize
data class Kilometers(
    @Json(name = "estimated_diameter_max")
    val estimatedDiameterMax: Double
) : Parcelable

@Parcelize
data class CloseApproachData(
    @Json(name = "close_approach_date")
    val closeApproachDate: String,
    @Json(name = "relative_velocity")
    val relativeVelocity: RelativeVelocity,
    @Json(name = "miss_distance")
    val missDistance: MissDistance
) : Parcelable

@Parcelize
data class RelativeVelocity(
    @Json(name = "kilometers_per_second")
    val kmPerSec: Double
) : Parcelable

@Parcelize
data class MissDistance(
    val astronomical: Double
) : Parcelable

fun List<Asteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            name = it.name,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}
