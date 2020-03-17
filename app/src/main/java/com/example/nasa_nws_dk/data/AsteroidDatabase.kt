package com.example.nasa_nws_dk.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.nasa_nws_dk.data.AsteroidDatabase.Companion.DB_NAME
import com.example.nasa_nws_dk.models.Asteroid

@Dao
interface AsteroidDao {
    //TODO: sort by date
    @Query("SELECT * FROM asteroid")
    fun getAll(): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: Asteroid)

    @Delete
    fun delete(asteroid: Asteroid)
}

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {

    abstract val asteroidDao: AsteroidDao

    companion object {
        const val DB_NAME = "db_asteroid"
    }
}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java, DB_NAME
            ).fallbackToDestructiveMigration()
                .build()
        }
    }

    return INSTANCE
}

