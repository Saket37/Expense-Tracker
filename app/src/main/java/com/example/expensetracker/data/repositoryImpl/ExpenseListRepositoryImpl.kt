package com.example.expensetracker.data.repositoryImpl

import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.domain.models.ExpenseListData
import com.example.expensetracker.domain.repository.ExpenseListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ExpenseListRepositoryImpl(
    private val expenseDao: ExpenseDao
) : ExpenseListRepository {
    override fun getExpensesAndTotal(
        startDate: Long,
        endDate: Long
    ): Flow<ExpenseListData> {
        return expenseDao.getExpensesForDateRange(startDate, endDate)
            .combine(expenseDao.getTotalForDateRange(startDate, endDate)) { expenses, total ->
                ExpenseListData(
                    expenses = expenses,
                    totalAmount = total ?: 0.0
                )
            }
    }
}