package com.example.expensetracker.data.repositoryImpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.expensetracker.domain.models.AppTheme
import com.example.expensetracker.domain.repository.AppThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class AppThemeRepositoryImpl(
    private val context: Context,
) : AppThemeRepository {
    override val theme: Flow<AppTheme> = context.dataStore.data
        .map { preferences ->
            val themeName = preferences[PreferencesKeys.THEME_KEY] ?: AppTheme.SYSTEM.name
            AppTheme.valueOf(themeName)
        }

    override suspend fun setTheme(theme: AppTheme) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_KEY] = theme.name
        }
    }

    private object PreferencesKeys {
        val THEME_KEY = stringPreferencesKey("theme_preference")
    }

}