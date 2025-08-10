package com.example.expensetracker.domain.repository

import com.example.expensetracker.data.model.CategoryTotal
import com.example.expensetracker.data.model.DailyTotal
import kotlinx.coroutines.flow.Flow

interface ReportRepository {

    fun getDailyTotalsForReport(startDate: Long, endDate: Long): Flow<List<DailyTotal>>

    fun getCategoryTotalsForReport(startDate: Long, endDate: Long): Flow<List<CategoryTotal>>

    fun getGrandTotalForReport(startDate: Long, endDate: Long): Flow<Double?>
}