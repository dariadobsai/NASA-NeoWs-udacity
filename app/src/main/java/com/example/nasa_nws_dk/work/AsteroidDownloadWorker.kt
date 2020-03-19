package com.example.nasa_nws_dk.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.nasa_nws_dk.data.getDatabase
import com.example.nasa_nws_dk.repo.AsteroidRepository

class AsteroidDownloadWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.refreshAsteroids()
            Result.success()

        } catch (e: Exception) {
            Result.retry()
        }
    }
}