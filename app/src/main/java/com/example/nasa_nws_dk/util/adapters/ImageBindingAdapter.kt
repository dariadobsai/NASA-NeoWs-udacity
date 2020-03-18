package com.example.nasa_nws_dk.util.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.nasa_nws_dk.R
import com.example.nasa_nws_dk.models.PictureOfDay
import com.squareup.picasso.Picasso


@BindingAdapter("image")
fun ImageView.bindPictureOfTheDay(pictureOfDay: String?) {
    pictureOfDay?.let {
         Picasso.get()
            .load(pictureOfDay)
            .placeholder(R.drawable.animation_ic_loading)
            .error(R.drawable.image_error)
            .into(this)
    }
}

@BindingAdapter("imageTitle")
fun ImageView.bindPictureOfTheDayTitle(pictureOfDay: String?) {
    pictureOfDay?.let {
        this.contentDescription = pictureOfDay
    }
}

/*@BindingAdapter("image")
fun ImageView.bindPictureOfTheDay(pictureOfDay: PictureOfDay) {
    pictureOfDay.let {
        if (it.mediaType != "image")
            this.setImageResource(R.drawable.ic_video_source)
        else Picasso.get()
            .load(it.url)
            .placeholder(R.drawable.animation_ic_loading)
            .error(R.drawable.image_error)
            .into(this)
    }
}

@BindingAdapter("imageTitle")
fun ImageView.bindPictureOfTheDayTitle(pictureOfDay: PictureOfDay) {
    pictureOfDay.let {
        this.contentDescription = it.title
    }
}*/

