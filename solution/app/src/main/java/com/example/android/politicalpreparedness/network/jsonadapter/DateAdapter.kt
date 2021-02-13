package com.example.android.politicalpreparedness.network.jsonadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    @FromJson
    fun dateFromString(dateString: String): Date = dateFormat.parse(dateString)!!

    @ToJson
    fun dateToString(date: Date): String = dateFormat.format(date)
}