package com.example.expensetracker.domain.repository

import com.example.expensetracker.domain.models.AppTheme
import kotlinx.coroutines.flow.Flow

interface AppThemeRepository {
    val theme: Flow<AppTheme>
    suspend fun setTheme(theme: AppTheme)
}