package com.example.expensetracker.di

import com.example.expensetracker.ui.add.AddExpenseViewModel
import com.example.expensetracker.ui.home.HomeScreenViewModel
import com.example.expensetracker.ui.report.ReportScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::AddExpenseViewModel)
    viewModelOf(::ReportScreenViewModel)
}