package com.example.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.expensetracker.data.local.entity.Expense
import com.example.expensetracker.data.model.CategoryTotal
import com.example.expensetracker.data.model.DailyTotal
import com.example.expensetracker.domain.models.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    /**
     * Fetches all expenses for a given date range (e.g., a single day).
     * This powers the "Expense List Screen" and returns a Flow to make the
     * UI update automatically.
     */
    @Query("SELECT * FROM expenses WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getExpensesForDateRange(startDate: Long, endDate: Long): Flow<List<Expense>>

    /**
     * Calculates the sum of amounts for a date range.
     * Used for the real-time "Total Spent Today" display at the top of the screen.
     */
    @Query("SELECT SUM(amount) FROM expenses WHERE date BETWEEN :startDate AND :endDate")
    fun getTotalForDateRange(startDate: Long, endDate: Long): Flow<Double?>

    /**
     * Gets the total amount spent per day for a given date range.
     * Used to generate data for the "Daily totals" bar/line chart in the report.
     */
    @Query(
        """
        SELECT date, SUM(amount) as total 
        FROM expenses 
        WHERE date BETWEEN :startDate AND :endDate 
        GROUP BY strftime('%Y-%m-%d', date / 1000, 'unixepoch')
    """
    )
    fun getDailyTotalsForReport(startDate: Long, endDate: Long): Flow<List<DailyTotal>>

    /**
     * Gets the total amount spent per category for a given date range.
     * Used for the "Category-wise totals" section of the report.
     */
    @Query(
        """
        SELECT category, SUM(amount) as total 
        FROM expenses 
        WHERE date BETWEEN :startDate AND :endDate 
        GROUP BY category
    """
    )
    fun getCategoryTotalsForReport(startDate: Long, endDate: Long): Flow<List<CategoryTotal>>


    @Query(
        """
    SELECT COUNT(*) FROM expenses
    WHERE title = :title AND amount = :amount AND category = :category AND date >= :sinceTimestamp
"""
    )
    suspend fun countSimilarExpenses(
        title: String,
        amount: Double,
        category: Category,
        sinceTimestamp: Long
    ): Int

}