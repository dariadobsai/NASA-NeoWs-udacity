package com.example.nasa_nws_dk.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.nasa_nws_dk.api.AsteroidApi
import com.example.nasa_nws_dk.data.AsteroidDatabase
import com.example.nasa_nws_dk.models.Asteroid
import com.example.nasa_nws_dk.models.asDatabaseModel
import com.example.nasa_nws_dk.models.asDomainModel
import com.example.nasa_nws_dk.util.Constants
import com.example.nasa_nws_dk.util.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAll()) {
        it.asDomainModel()

        /**
         * The detailed description about this commented line code below be found in the AsteroidDatabase class.
         *
         * 1. Sorting steroids in the descending order by date
         * 2. Call asDomainModel() on the sorted list
         *
         *   val sorted = sortedBy {
         *       it.closeApproachData[0].approachDate
         *   }
         *   sorted.asDomainModel()
         *
         */
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val asteroids = AsteroidApi.retrofitService.getAsteroids(
                LocalDate.now(),
                LocalDate.now().plusDays(Constants.DEFAULT_END_DATE_DAYS),
                API_KEY
            ).await()

            database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
        }
    }
}
