package com.example.expensetracker.ui.home

import com.example.expensetracker.domain.models.AppTheme

sealed interface HomeScreenEvent {
    data class FilterSelected(val displayName: String) : HomeScreenEvent
    data class GroupingChanged(val groupBy: String) : HomeScreenEvent
    data class PeriodStartDateSelected(val date: Long) : HomeScreenEvent
    data class PeriodEndDateSelected(val date: Long) : HomeScreenEvent
    data class SetTheme(val theme: AppTheme) : HomeScreenEvent
}