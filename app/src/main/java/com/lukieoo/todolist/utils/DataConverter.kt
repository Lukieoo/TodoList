package com.lukieoo.todolist.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Object for converting from milliSeconds to String Date
 */
object DataConverter {
    /**
     * @param milliSeconds  your time in milliSecond
     * @param dateFormat your date format like "dd/MM/yyyy HH:mm"
     */
     fun getDate(milliSeconds: Long, dateFormat: String): String {
        val formatter = SimpleDateFormat(dateFormat)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }
}