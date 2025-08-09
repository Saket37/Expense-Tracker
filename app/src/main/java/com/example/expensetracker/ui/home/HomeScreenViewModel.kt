package com.example.expensetracker.ui.home

import androidx.lifecycle.ViewModel
import com.example.expensetracker.data.local.entity.Expense
import com.example.expensetracker.domain.models.DateFilterType
import com.example.expensetracker.domain.models.GroupingType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import java.util.Date


data class ExpenseListUiState(
    val expenses: List<Expense> = emptyList(),
    val totalAmount: Double = 0.0,
    val totalCount: Int = 0,

    val selectedTab: DateFilterType = DateFilterType.TODAY,
    val selectedGrouping: GroupingType = GroupingType.BY_TIME,
    val customStartDate: Date? = null,
    val customEndDate: Date? = null,

    //  val isLoading: Boolean = true,
)

class HomeScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseListUiState())
    val uiState = _uiState.asStateFlow()

    init {

    }


    private fun getStartOfDay(date: Date): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.time
    }
}