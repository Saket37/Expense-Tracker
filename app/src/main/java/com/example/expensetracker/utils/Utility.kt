package com.example.expensetracker.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

object Utility {
    fun formatDateTime(timeInMillis: Long): String {
        val instant = Instant.ofEpochMilli(timeInMillis)
        val formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm")
        val zonedDateTime = instant.atZone(ZoneId.systemDefault())

        return zonedDateTime.format(formatter)
    }
    fun formatDate(timeInMillis: Long): String {
        val instant = Instant.ofEpochMilli(timeInMillis)
        val formatter = DateTimeFormatter.ofPattern("dd/MMM/yy")
            .withZone(ZoneId.systemDefault())

        return formatter.format(instant)
    }
    fun getTimestampForDate(year: Int, month: Int, day: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day, 12, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}