package com.example.expensetracker.ui.report

import androidx.lifecycle.ViewModel
import com.example.expensetracker.data.local.entity.Expense
import com.example.expensetracker.data.model.CategoryTotal
import com.example.expensetracker.domain.repository.ReportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class ExpenseReportState(
    val grandTotal: Double = 0.0,
    val dateRangeLabel: String = "",
    val categoryTotals: List<CategoryTotal> = emptyList(),
    val error: String? = null,
    val barDetails: List<BarDetails> = emptyList()
)

class ReportScreenViewModel(private val reportRepository: ReportRepository) : ViewModel() {

    private val _state = MutableStateFlow(ExpenseReportState())
    val state: StateFlow<ExpenseReportState> = _state.asStateFlow()

    init {
        _state.value = prepareExpenseReport(expenses = mockExpenses)
    }

    private fun prepareExpenseReport(expenses: List<Expense>): ExpenseReportState {
        if (expenses.isEmpty()) {
            return ExpenseReportState(
                error = "No expenses found for the selected range."
            )
        }
        val grandTotal = expenses.sumOf { it.amount }
        val categoryTotals = expenses
            .groupBy { it.category }
            .map { (category, list) ->
                CategoryTotal(category, list.sumOf { it.amount })
            }
        val dateFormat = SimpleDateFormat("d MMM", Locale.getDefault())

        val barDetails = expenses
            .groupBy { dateFormat.format(Date(it.date)) }
            .map { (label, list) ->
                val total = list.sumOf { it.amount }
                BarDetails(
                    label = label,
                    barValue = total.toFloat(),
                    barText = "₹%.2f".format(total)
                )
            }
            .sortedBy {
                dateFormat.parse(it.label)?.time
            }


        val minDate = expenses.minOf { it.date }
        val maxDate = expenses.maxOf { it.date }
        val dateRangeLabel =
            "${dateFormat.format(Date(minDate))} - ${dateFormat.format(Date(maxDate))}"

        return ExpenseReportState(
            grandTotal = grandTotal,
            dateRangeLabel = dateRangeLabel,
            categoryTotals = categoryTotals,
            barDetails = barDetails
        )
    }
}