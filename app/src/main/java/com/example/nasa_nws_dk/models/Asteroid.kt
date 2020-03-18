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
    val closeApproachData: List<CloseApproachData>,
    @Json(name = "absolute_magnitude_h")
    val absoluteMagnitude: Double,
    @Json(name = "estimated_diameter")
    val estimatedDiameter: EstimatedDiameter,
    @Json(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardous: Boolean
) : Parcelable {

    constructor() : this(
        0,
        "",
        emptyList(),
        0.0,
        EstimatedDiameter(Kilometers(0.0)),
        false
    )
}


@Parcelize
data class EstimatedDiameter(
    val kilometers: Kilometers = Kilometers(0.0)
) : Parcelable

@Parcelize
data class Kilometers(
    @Json(name = "estimated_diameter_max")
    val estimatedDiameterMax: Double = 0.0
) : Parcelable

@Parcelize
data class CloseApproachData(
    @Json(name = "close_approach_date")
    val approachDate: String = "",
    @Json(name = "relative_velocity")
    val relativeVelocity: RelativeVelocity = RelativeVelocity(0.0),
    @Json(name = "miss_distance")
    val missDistance: MissDistance = MissDistance(0.0)
) : Parcelable

@Parcelize
data class RelativeVelocity(
    @Json(name = "kilometers_per_second")
    val kmPerSec: Double = 0.0
) : Parcelable

@Parcelize
data class MissDistance(
    val astronomical: Double = 0.0
) : Parcelable

fun List<Asteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            name = it.name,
            closeApproachData = it.closeApproachData,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}
