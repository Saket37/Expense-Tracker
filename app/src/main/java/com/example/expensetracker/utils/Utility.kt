package com.example.expensetracker.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Utility {
    fun formatDateTime(timeInMillis: Long): String {
        val instant = Instant.ofEpochMilli(timeInMillis)
        val formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm")
        val zonedDateTime = instant.atZone(ZoneId.systemDefault())

        return zonedDateTime.format(formatter)
    }
}