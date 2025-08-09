package com.example.expensetracker.domain.models

enum class DateFilterType(val displayName: String) {
    TODAY("Today"),
    LAST_7_DAYS("Past 7 Days"),
    PERIOD("Period")
}