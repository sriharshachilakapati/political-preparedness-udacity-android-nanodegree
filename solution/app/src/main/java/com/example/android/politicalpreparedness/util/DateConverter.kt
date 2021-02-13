package com.example.android.politicalpreparedness.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    private val dateFormat: DateFormat = SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH)

    @JvmStatic
    fun toSimpleString(date: Date): String = dateFormat.format(date)
}