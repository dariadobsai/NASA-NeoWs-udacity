package com.example.nasa_nws_dk.api

import com.example.nasa_nws_dk.models.AsteroidResponse
import com.example.nasa_nws_dk.models.PictureOfDay
import com.example.nasa_nws_dk.util.Constants
import com.example.nasa_nws_dk.util.Constants.APOD_URL
import com.example.nasa_nws_dk.util.Constants.NEO_FEED_URL
import com.example.nasa_nws_dk.util.DateAdapter
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface AsteroidApiInterface {

    @GET(NEO_FEED_URL)
    fun getAsteroids(
        @Query("start_date") startDate: LocalDate,
        @Query("end_date") endDate: LocalDate,
        @Query("api_key") apiKey: String
    ): Deferred<AsteroidResponse>


    @GET(APOD_URL)
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String
    ): Deferred<PictureOfDay>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(DateAdapter())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.BASE_URL)
    .build()

object AsteroidApi {
    val retrofitService: AsteroidApiInterface by lazy {
        retrofit.create(AsteroidApiInterface::class.java)
    }
}