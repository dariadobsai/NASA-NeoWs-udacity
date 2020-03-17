package com.example.nasa_nws_dk.data.converters

import androidx.room.TypeConverter
import com.example.nasa_nws_dk.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private val gson = Gson()

object CloseApproachDataConverter {

    @TypeConverter
    fun toCloseApproachDataString(str: String): List<CloseApproachData> {
        val type = object : TypeToken<List<CloseApproachData>>() {}.type
        return gson.fromJson(str, type)
    }

    @TypeConverter
    fun toCloseApproachDataList(list: List<CloseApproachData>): String {
        val type = object : TypeToken<List<CloseApproachData>>() {}.type
        return gson.toJson(list, type)
    }
}

object RelativeVelocityConverter {

    @TypeConverter
    fun stringToDouble(data: String): Double {

        val doubleType = object : TypeToken<Double>() {}.type

        return gson.fromJson<Double>(data, doubleType)
    }

    @TypeConverter
    fun doubleToString(d: Double): String {
        return gson.toJson(d)
    }
}

object MissDistanceConverter {

    @TypeConverter
    fun stringToDouble(data: String?): MissDistance {
        val doubleType = object : TypeToken<RelativeVelocity>() {}.type

        return gson.fromJson<MissDistance>(data, doubleType)
    }

    @TypeConverter
    fun doubleToString(someObjects: MissDistance): String {
        return gson.toJson(someObjects)
    }
}

object EstimatedDiameterConverter {

    @TypeConverter
    fun stringToJson(data: String?): EstimatedDiameter {
        val doubleType = object : TypeToken<RelativeVelocity>() {}.type

        return gson.fromJson<EstimatedDiameter>(data, doubleType)
    }

    @TypeConverter
    fun jsonToString(someObjects: EstimatedDiameter): String {
        return gson.toJson(someObjects)
    }
}

object KilometersConverter {

    @TypeConverter
    fun stringToDouble(data: String?): Kilometers {
        val doubleType = object : TypeToken<RelativeVelocity>() {}.type

        return gson.fromJson<Kilometers>(data, doubleType)
    }

    @TypeConverter
    fun doubleToString(someObjects: Kilometers): String {
        return gson.toJson(someObjects)
    }
}




