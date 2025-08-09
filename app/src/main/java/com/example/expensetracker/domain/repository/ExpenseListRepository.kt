package com.example.expensetracker.domain.repository

import com.example.expensetracker.domain.models.ExpenseListData
import kotlinx.coroutines.flow.Flow

interface ExpenseListRepository {
    fun getExpensesAndTotal(startDate: Long, endDate: Long): Flow<ExpenseListData>
}