package com.example.expensetracker.data.repositoryImpl

import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.model.CategoryTotal
import com.example.expensetracker.data.model.DailyTotal
import com.example.expensetracker.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow

class ReportRepositoryImpl(private val dao: ExpenseDao) : ReportRepository {
    override fun getDailyTotalsForReport(
        startDate: Long,
        endDate: Long
    ): Flow<List<DailyTotal>> {
        return dao.getDailyTotalsForReport(startDate, endDate)
    }

    override fun getCategoryTotalsForReport(
        startDate: Long,
        endDate: Long
    ): Flow<List<CategoryTotal>> {
        return dao.getCategoryTotalsForReport(startDate, endDate)
    }

    override fun getGrandTotalForReport(
        startDate: Long,
        endDate: Long
    ): Flow<Double?> {
        return dao.getTotalForDateRange(startDate, endDate)
    }
}