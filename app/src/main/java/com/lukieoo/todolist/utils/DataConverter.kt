package com.lukieoo.todolist.utils

import java.text.SimpleDateFormat
import java.util.*

object DataConverter {
    open fun getDate(milliSeconds: Long, dateFormat: String): String {
        var formatter: SimpleDateFormat = SimpleDateFormat(dateFormat)
        var calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.getTime())
    }
}