package com.example.expensetracker.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetracker.data.local.CategoryTypeConvertor
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.entity.Expense

@Database(entities = [Expense::class], version = 1, exportSchema = true)
@TypeConverters(CategoryTypeConvertor::class)
abstract class ExpenseTrackerDB : RoomDatabase() {
    abstract val expenseDao: ExpenseDao
}