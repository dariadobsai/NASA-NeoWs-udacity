package com.example.nasa_nws_dk

import android.app.Application
import androidx.work.*
import com.example.nasa_nws_dk.util.Constants.WORK_NAME
import com.example.nasa_nws_dk.work.AsteroidDownloadWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    val applicationScope = CoroutineScope(Dispatchers.Default)
    private lateinit var workManager : WorkManager

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
            isAnyWorkScheduled(workManager)
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<AsteroidDownloadWorker>(1, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .addTag("tag")
            .build()

        workManager = WorkManager.getInstance(applicationContext)

        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    fun isAnyWorkScheduled(workManager: WorkManager): Boolean {
        return try {
            Timber.v("scheduled")
            workManager.getWorkInfosByTag("tag").get().firstOrNull { !it.state.isFinished } != null
        } catch (e: Exception) {
            when (e) {
                is ExecutionException, is InterruptedException -> {
                    Timber.v("not scheduled")
                }
                else -> throw e
            }
            false
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        delayedInit()
    }
}