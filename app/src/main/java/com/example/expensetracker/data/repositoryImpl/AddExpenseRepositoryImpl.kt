package com.example.expensetracker.data.repositoryImpl

import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.entity.Expense
import com.example.expensetracker.domain.repository.AddExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId

class AddExpenseRepositoryImpl(
    private val dao: ExpenseDao
) : AddExpenseRepository {
    override fun getTodayTotalExpense(): Flow<Double> {
        val startOfDay = getStartOfDayInMillis()
        val endOfDay = getEndOfDayInMillis()

        return dao.getTotalForDateRange(startOfDay, endOfDay)
            .map { it ?: 0.0 }
    }

    override suspend fun insertExpense(expense: Expense) {
        dao.insertExpense(expense)
    }

    override suspend fun isDuplicateExpense(expense: Expense, sinceTimestamp: Long): Boolean {
        val count = dao.countSimilarExpenses(
            title = expense.title,
            amount = expense.amount,
            category = expense.category,
            sinceTimestamp = sinceTimestamp
        )
        return count > 0
    }

    private fun getStartOfDayInMillis(): Long {
        val now = LocalDate.now()
        return now.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    private fun getEndOfDayInMillis(): Long {
        val now = LocalDate.now()
        return now.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1
    }
}