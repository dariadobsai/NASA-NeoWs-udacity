package com.example.nasa_nws_dk

import android.app.Application
import androidx.work.*
import com.example.nasa_nws_dk.util.Constants.WORK_NAME
import com.example.nasa_nws_dk.work.AsteroidDownloadWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    val applicationScope = CoroutineScope(Dispatchers.Default)
    private lateinit var workManager: WorkManager

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<AsteroidDownloadWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        workManager = WorkManager.getInstance(applicationContext)

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }
}