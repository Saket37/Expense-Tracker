package com.example.expensetracker.di

import com.example.expensetracker.data.repositoryImpl.AddExpenseRepositoryImpl
import com.example.expensetracker.data.repositoryImpl.AppThemeRepositoryImpl
import com.example.expensetracker.data.repositoryImpl.ExpenseListRepositoryImpl
import com.example.expensetracker.domain.repository.AddExpenseRepository
import com.example.expensetracker.domain.repository.AppThemeRepository
import com.example.expensetracker.domain.repository.ExpenseListRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<AppThemeRepository> {
        AppThemeRepositoryImpl(androidContext())
    }

    single<ExpenseListRepository> {
        ExpenseListRepositoryImpl(get())
    }
    single<AddExpenseRepository> {
        AddExpenseRepositoryImpl(get())
    }
}