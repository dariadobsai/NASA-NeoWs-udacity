package com.example.nasa_nws_dk.main.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nasa_nws_dk.api.AsteroidApi
import com.example.nasa_nws_dk.data.getDatabase
import com.example.nasa_nws_dk.models.PictureOfDay
import com.example.nasa_nws_dk.repo.AsteroidRepository
import com.example.nasa_nws_dk.util.Constants.API_KEY
import com.example.nasa_nws_dk.util.isOnline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(application: Application, context: Context) :
    AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    private val _apod = MutableLiveData<PictureOfDay>()
    val apod: LiveData<PictureOfDay>
        get() = _apod

    /* private val _asteroidResponse = MutableLiveData<List<Asteroid>>()
     val asteroidResponse: LiveData<List<Asteroid>>
         get() = _asteroidResponse*/

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        if (isOnline(context)) {
            loadPictureOfTheDay()
            coroutineScope.launch {
                asteroidRepository.refreshAsteroids()
            }
        }
    }

    val asteroids = asteroidRepository.asteroids

    /*
      fun scheduleWork() {
          val dataDownload =
              PeriodicWorkRequest.Builder(AsteroidDownloadWorker::class.java, 1, TimeUnit.DAYS)
          val request = dataDownload.build()
          WorkManager.getInstance(getApplication())
              .enqueueUniquePeriodicWork("tag", ExistingPeriodicWorkPolicy.KEEP, request)
      }*/

    private fun loadPictureOfTheDay() {
        coroutineScope.launch {
            val getAsteroidDeferred = AsteroidApi.retrofitService.getPictureOfTheDay(API_KEY)
            val result = getAsteroidDeferred.await()

            try {
                _apod.value = result
            } catch (t: Throwable) {
                _errorMessage.value = t.message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}