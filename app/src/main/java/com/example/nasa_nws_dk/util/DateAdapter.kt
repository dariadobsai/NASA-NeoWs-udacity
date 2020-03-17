package com.example.nasa_nws_dk.util

import com.squareup.moshi.FromJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateAdapter {

    @FromJson
    fun fromJson(value: String): LocalDate = LocalDate.parse(value, FORMATTER)

    companion object {
        val FORMATTER = DateTimeFormatter.ofPattern(Constants.API_QUERY_DATE_FORMAT)
    }

}