package com.example.nasa_nws_dk.util.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.nasa_nws_dk.R
import com.example.nasa_nws_dk.models.PictureOfDay
import com.squareup.picasso.Picasso

@BindingAdapter("image")
fun ImageView.bindPictureOfTheDay(pictureOfDay: PictureOfDay?) {
    pictureOfDay?.let {
        if (it.mediaType == "image") {
            Picasso.get()
                .load(it.url)
                .placeholder(R.drawable.animation_ic_loading)
                // This image is also triggered when the work is triggered on the background, since the image is never downloaded on the background
                .error(R.drawable.image_error)
                .into(this)
        } else this.setImageResource(R.drawable.empty_picture_of_day)
    }
}

@BindingAdapter("imageTitle")
fun ImageView.bindPictureOfTheDayTitle(pictureOfDay: PictureOfDay?) {
    pictureOfDay?.let {
        this.contentDescription = it.title
    }
}

