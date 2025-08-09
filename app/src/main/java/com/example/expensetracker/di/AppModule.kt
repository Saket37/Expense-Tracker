package com.example.expensetracker.di

import com.example.expensetracker.data.repositoryImpl.AppThemeRepositoryImpl
import com.example.expensetracker.domain.repository.AppThemeRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<AppThemeRepository> {
        AppThemeRepositoryImpl(androidContext())
    }
}