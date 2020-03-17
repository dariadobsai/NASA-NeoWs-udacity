package com.example.nasa_nws_dk.util.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.nasa_nws_dk.R
import com.example.nasa_nws_dk.models.Asteroid
import com.example.nasa_nws_dk.models.CloseApproachData

@BindingAdapter("statusIcon")
fun ImageView.bindAsteroidStatusImage(asteroid: Asteroid) {
    this.setImageResource(
        when (asteroid.isPotentiallyHazardous) {
            true -> R.drawable.ic_status_potentially_hazardous
            else -> R.drawable.ic_status_normal
        }
    )
}

@BindingAdapter("name")
fun TextView.bindTextViewToDisplayAsteroidName(asteroid: Asteroid) {
    this.text = asteroid.name
}

@BindingAdapter("date")
fun TextView.bindTextViewToDisplayAsteroidDate(asteroid: Asteroid) {
    this.text = asteroid.getCloseApproachDataObject().closeApproachDate
}


@BindingAdapter("asteroidStatusImage")
fun ImageView.bindDetailsStatusImage(asteroid: Asteroid) {
    this.setImageResource(
        when (asteroid.isPotentiallyHazardous) {
            true -> R.drawable.asteroid_hazardous
            else -> R.drawable.asteroid_safe
        }
    )
}

@BindingAdapter("asteroidStatusImageDescription")
fun ImageView.bindDetailsStatusImageDescription(asteroid: Asteroid) {
    this.contentDescription = (
            when (asteroid.isPotentiallyHazardous) {
                true -> context.getString(R.string.desc_img_asteroid_status_hazardous)
                else -> context.getString(R.string.desc_img_asteroid_status_not_hazardous)
            })
}

@BindingAdapter("astronomicalUnitText")
fun TextView.bindTextViewToAstronomicalUnit(asteroid: Asteroid) {
    val magnitude = asteroid.absoluteMagnitude
    this.text = String.format(context.getString(R.string.astronomical_unit_format), magnitude)
}

@BindingAdapter("kmUnitText")
fun TextView.bindTextViewToKmUnit(asteroid: Asteroid) {
    val km = asteroid.estimatedDiameter.kilometers.estimatedDiameterMax
    this.text = String.format(context.getString(R.string.km_unit_format), km)
}

@BindingAdapter("velocityText")
fun TextView.bindTextViewToDisplayVelocity(asteroid: Asteroid) {
    val velocity = asteroid.getCloseApproachDataObject().relativeVelocity.kmPerSec
    this.text = String.format(context.getString(R.string.km_s_unit_format), velocity)
}

@BindingAdapter("distance")
fun TextView.bindTextViewToDisplayDistanceFromEarth(asteroid: Asteroid) {
    val astronomical = asteroid.getCloseApproachDataObject().missDistance.astronomical
    this.text = String.format(context.getString(R.string.astronomical_unit_format), astronomical)
}

private fun Asteroid.getCloseApproachDataObject(): CloseApproachData {
    return this.closeApproachDate[0]
}