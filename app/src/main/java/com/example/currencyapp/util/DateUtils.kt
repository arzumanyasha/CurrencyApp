package com.example.currencyapp.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun getCurrentDate() : String {
        val calendarTime = Calendar.getInstance().time
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendarTime)
    }
}