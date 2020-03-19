package com.example.nasa_nws_dk.data.converters

import androidx.room.TypeConverter
import com.example.nasa_nws_dk.models.CloseApproachData
import com.example.nasa_nws_dk.models.EstimatedDiameter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private val gson = Gson()

class Converters {

    @TypeConverter
    fun toCloseApproachDataString(str: String): List<CloseApproachData>? {
        val type = object : TypeToken<List<CloseApproachData>>() {}.type
        return gson.fromJson(str, type)
    }

    @TypeConverter
    fun toCloseApproachDataJson(list: List<CloseApproachData>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toEstimatedDiameterString(data: String?): EstimatedDiameter {
        val type = object : TypeToken<EstimatedDiameter>() {}.type
        return gson.fromJson<EstimatedDiameter>(data, type)
    }

    @TypeConverter
    fun toEstimatedDiameterStringJson(estimatedDiameter: EstimatedDiameter): String {
        return gson.toJson(estimatedDiameter)
    }
}




