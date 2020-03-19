package com.example.nasa_nws_dk.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

class HelperFunctions {
    companion object {

        fun View.showSnackBar(msg: String) {
            Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()
        }
    }
}

