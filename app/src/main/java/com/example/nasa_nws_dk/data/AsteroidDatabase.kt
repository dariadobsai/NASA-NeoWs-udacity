package com.example.nasa_nws_dk.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nasa_nws_dk.data.AsteroidDatabase.Companion.DB_NAME
import com.example.nasa_nws_dk.data.converters.Converters
import com.example.nasa_nws_dk.models.Asteroid

@Dao
interface AsteroidDao {

    /**
     * Problematic: Because closeApproachData is a List<CloseApproachData> and stored as a String,
     * there is no way to refer to the individual fields.
     *
     * 1. I tried to use JSON_EXTRACT based on the following link -> https://stackoverflow.com/questions/45137881/sort-by-json-field-values
     * @Query("SELECT *, JSON_EXTRACT (closeApproachData, '\$.close_approach_date') AS approachDate FROM asteroid ORDER BY approachDate DESC")
     * As I found out "JSON_EXTRACT" cannot be applied in Room
     *
     * 2. I tried to pass "closeApproachData" as it is.
     * It works, since it is the the first field in the JSON response, so that is why I mentioned that it is based on luck.
     * I understand that in case JSON response is modified such an approach is not usable.
     *
     * 3.The following solution was implemented the way it is, because of the Project Instructions specifications that required
     * as usage of Query, however in this particular project, the best way would be to do it directly in code,
     * so I left a commented out solution in the AsteroidRepository class, that demonstrates how it can be done.
     */

    @Query("SELECT * FROM asteroid ORDER BY closeApproachData ASC")
    fun getAll(): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: Asteroid)

    @Delete
    fun delete(asteroid: Asteroid)
}

@Database(entities = [Asteroid::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
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
            ).addMigrations(MIGRATION_1_2)
                .build()
        }
    }

    return INSTANCE
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE 'asteroids'")
    }
}

