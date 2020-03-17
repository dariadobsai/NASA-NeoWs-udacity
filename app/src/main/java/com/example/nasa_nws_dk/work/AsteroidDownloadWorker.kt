package com.example.nasa_nws_dk.work

import android.content.Context
import androidx.work.*


class AsteroidDownloadWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        try {
            /* val work = downloadNextData()
             // Create the output of the work
             val response = work.body()*/

            return Result.success()

        } catch (e: Exception) {
            return Result.failure()
        }
    }


/*
    fun loadAsteroidsData() {
        coroutineScope.launch {

            val getAsteroidDeferred = AsteroidApi.retrofitService.getAsteroids(
                LocalDate.now(),
                LocalDate.now().plusDays(Constants.DEFAULT_END_DATE_DAYS),
                Constants.API_KEY
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

    // Create a Constraints object that defines when the task should run
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresCharging(true)
        .build()


    val downloadWorkRequest = OneTimeWorkRequestBuilder<AsteroidDownloadWorker>()
        .setConstraints(constraints)
        .build()
}