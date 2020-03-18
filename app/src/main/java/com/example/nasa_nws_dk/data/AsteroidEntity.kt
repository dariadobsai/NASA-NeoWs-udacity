package com.example.nasa_nws_dk.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.nasa_nws_dk.models.Asteroid
import com.example.nasa_nws_dk.models.CloseApproachData
import com.example.nasa_nws_dk.models.EstimatedDiameter

/*
@Entity
data class DatabaseAsteroid constructor(
    @PrimaryKey
    val id: Long,
    val name: String,
    @TypeConverters(CloseApproachDataConverter::class)
    val closeApproachDate: List<CloseApproachData>,
    val absoluteMagnitude: Double,
    val estimatedDiameter: EstimatedDiameter,
    val isPotentiallyHazardous: Boolean
)

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
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

*/
