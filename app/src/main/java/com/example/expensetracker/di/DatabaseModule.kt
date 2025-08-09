package com.example.expensetracker.di

import androidx.room.Room
import com.example.expensetracker.data.local.db.ExpenseTrackerDB
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(), ExpenseTrackerDB::class.java, "expense.db"
        ).build()
    }

    single {
        get<ExpenseTrackerDB>().expenseDao
    }
}