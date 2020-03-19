package com.example.nasa_nws_dk.main.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.nasa_nws_dk.api.AsteroidApi
import com.example.nasa_nws_dk.data.getDatabase
import com.example.nasa_nws_dk.models.PictureOfDay
import com.example.nasa_nws_dk.repo.AsteroidRepository
import com.example.nasa_nws_dk.util.Constants.API_KEY
import com.example.nasa_nws_dk.util.NetworkConnectivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(application: Application, context: Context) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)
    private var networkConnectivity: LiveData<Boolean> = NetworkConnectivity(context)

    private val _apod = MutableLiveData<PictureOfDay>()
    val apod: LiveData<PictureOfDay>
        get() = _apod

    private val networkObserver = Observer<Boolean> {
        if (it == true) {
            loadPictureOfTheDay()
            viewModelScope.launch {
                asteroidRepository.refreshAsteroids()
            }
        }
    }

    init {
        networkConnectivity.observeForever(networkObserver)
    }

    val asteroids = asteroidRepository.asteroids

    private fun loadPictureOfTheDay() {
        viewModelScope.launch {
            val getAsteroidDeferred = AsteroidApi.retrofitService.getPictureOfTheDay(API_KEY)
            val result = getAsteroidDeferred.await()

            try {
                _apod.value = result
            } catch (t: Throwable) {
                Timber.d(t)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        networkConnectivity.removeObserver(networkObserver)
        viewModelJob.cancel()
    }
}