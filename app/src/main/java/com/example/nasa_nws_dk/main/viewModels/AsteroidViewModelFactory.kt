package com.example.nasa_nws_dk.main.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class AsteroidViewModelFactory(val application: Application, val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(application, context) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}