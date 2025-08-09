package com.example.expensetracker.domain.repository

import com.example.expensetracker.data.local.entity.Expense
import kotlinx.coroutines.flow.Flow

interface AddExpenseRepository {
    fun getTodayTotalExpense(): Flow<Double>
    suspend fun insertExpense(expense: Expense)
    suspend fun isDuplicateExpense(expense: Expense, sinceTimestamp: Long): Boolean
}