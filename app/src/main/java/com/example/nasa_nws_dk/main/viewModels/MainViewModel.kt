package com.example.nasa_nws_dk.main.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nasa_nws_dk.api.AsteroidApi
import com.example.nasa_nws_dk.data.getDatabase
import com.example.nasa_nws_dk.models.PictureOfDay
import com.example.nasa_nws_dk.repo.AsteroidRepository
import com.example.nasa_nws_dk.util.Constants.API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(application: Application) :
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
        loadPictureOfTheDay()

        coroutineScope.launch {
            asteroidRepository.refreshAsteroids()
        }
    }

    val asteroids = asteroidRepository.asteroids

    /*fun loadAsteroidsData() {
        coroutineScope.launch {

            val getAsteroidDeferred = AsteroidApi.retrofitService.getAsteroids(
                LocalDate.now(),
                LocalDate.now().plusDays(DEFAULT_END_DATE_DAYS),
                API_KEY
            )

            val result = getAsteroidDeferred.await()

            try {
                //_errorMessage.value = "Retrieved: ${result.nearEarthObjects.size} asteroids"
                _asteroidResponse.value = result.nearEarthObjects.flatMap {
                    it.value
                }.sortedBy { it.closeApproachDate[0].closeApproachDate }

            } catch (t: Throwable) {
                _errorMessage.value = t.message

            }
        }
    }*/

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