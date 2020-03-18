package com.example.nasa_nws_dk.data.converters

import androidx.room.TypeConverter
import com.example.nasa_nws_dk.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Moshi

private val gson = Gson()

private val moshi = Moshi.Builder().build()

class Converters {

    @TypeConverter
    fun toCloseApproachDataString(str: String): List<CloseApproachData>? {
        val type = object : TypeToken<List<CloseApproachData>>() {}.type
        return gson.fromJson(str, type)
    }

    @TypeConverter
    fun toCloseApproachDataList(list: List<CloseApproachData>): String {
        return gson.toJson(list)
    }


    @TypeConverter
    fun stringToDouble(data: String): Double {

        val doubleType = object : TypeToken<Double>() {}.type

        return gson.fromJson<Double>(data, doubleType)
    }

    @TypeConverter
    fun doubleToString(d: Double): String {
        return gson.toJson(d)
    }


    @TypeConverter
    fun stringToDouble(data: String?): MissDistance {
        val doubleType = object : TypeToken<RelativeVelocity>() {}.type

        return gson.fromJson<MissDistance>(data, doubleType)
    }

    @TypeConverter
    fun doubleToString(someObjects: MissDistance): String {
        return gson.toJson(someObjects)
    }


    @TypeConverter
    fun stringToJson(data: String?): EstimatedDiameter {
        val stringType = object : TypeToken<EstimatedDiameter>() {}.type

        return gson.fromJson<EstimatedDiameter>(data, stringType)
    }

    @TypeConverter
    fun jsonToString(estimatedDiameter: EstimatedDiameter): String {
        return gson.toJson(estimatedDiameter)
    }


    @TypeConverter
    fun stringToDoubleKm(data: String?): Kilometers {
        val doubleType = object : TypeToken<Kilometers>() {}.type

        return gson.fromJson<Kilometers>(data, doubleType)
    }

    @TypeConverter
    fun doubleToString(kilometers: Kilometers): String {
        return gson.toJson(kilometers)
    }
}




