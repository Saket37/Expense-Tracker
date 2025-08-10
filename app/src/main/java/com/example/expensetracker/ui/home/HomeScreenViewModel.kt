package com.example.expensetracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.entity.Expense
import com.example.expensetracker.domain.models.Category
import com.example.expensetracker.domain.models.DateFilterType
import com.example.expensetracker.domain.models.GroupingType
import com.example.expensetracker.domain.repository.ExpenseListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

sealed interface ExpenseListItem {
    /**
     * Represents a single expense entry when grouping by time.
     */
    data class ExpenseItem(
        val id: Long,
        val description: String,
        val amount: Double,
        val category: Category,
        val date: Long
    ) : ExpenseListItem

    /**
     * Represents a summary for a category when grouping by category.
     */
    data class CategorySummaryItem(
        val category: Category,
        val totalAmount: Double,
        val expenseCount: Int,
        // You could also add other useful info, like percentage of total
        // val percentageOfTotal: Float
    ) : ExpenseListItem
}

data class ExpenseListUiState(
    val selectedDateFilter: DateFilterType = DateFilterType.TODAY,
    val selectedGroupBy: GroupingType = GroupingType.BY_TIME,
    val isLoading: Boolean = true,
    val totalAmount: Double = 0.0,
    val totalCount: Int = 0,
    val displayItems: List<ExpenseListItem> = emptyList(),
    val errorMessage: String? = null,
    val periodStartDate: Long? = null,
    val periodEndDate: Long? = null
)

class HomeScreenViewModel(
    private val expenseListRepository: ExpenseListRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeFilterChanges()
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.FilterSelected -> onDateFilterChangedByDisplayName(event.displayName)
            is HomeScreenEvent.GroupingChanged -> onGroupByChanged(event.groupBy)
            is HomeScreenEvent.PeriodStartDateSelected -> onPeriodStartDateChanged(event.date)
            is HomeScreenEvent.PeriodEndDateSelected -> onPeriodEndDateChanged(event.date)
        }
    }

    private fun observeFilterChanges() {
        viewModelScope.launch {
            combine(
                _uiState.map { it.selectedDateFilter }.distinctUntilChanged(),
                _uiState.map { it.selectedGroupBy }.distinctUntilChanged(),
                _uiState.map { it.periodStartDate }.distinctUntilChanged(),
                _uiState.map { it.periodEndDate }.distinctUntilChanged()
            ) { dateFilter, groupBy, startDate, endDate ->
                Pair(dateFilter, groupBy)
            }.collect { (dateFilter, groupBy) ->
                fetchAndProcessExpenses(dateFilter, groupBy)
            }
        }
    }

    private fun fetchAndProcessExpenses(dateFilter: DateFilterType, groupBy: GroupingType) {
        _uiState.update { it.copy(isLoading = true) }

        val (startDate, endDate) = calculateDateRange(dateFilter)

        viewModelScope.launch {
            expenseListRepository.getExpensesAndTotal(startDate, endDate)
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Failed to load data: ${e.message}"
                        )
                    }
                }
                .collect { expenseListData ->
                    val displayItems = when (groupBy) {
                        GroupingType.BY_TIME -> processForTimeGroup(expenseListData.expenses)
                        GroupingType.BY_CATEGORY -> processForCategoryGroup(expenseListData.expenses)
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            totalAmount = expenseListData.totalAmount,
                            totalCount = expenseListData.expenses.size,
                            displayItems = displayItems,
                            errorMessage = null
                        )
                    }
                }
        }
    }

    private fun processForTimeGroup(expenses: List<Expense>): List<ExpenseListItem.ExpenseItem> {
        return expenses.map { expense ->
            ExpenseListItem.ExpenseItem(
                id = expense.id,
                description = expense.notes.toString(),
                amount = expense.amount,
                category = expense.category,
                date = expense.date
            )
        }
    }

    private fun processForCategoryGroup(expenses: List<Expense>): List<ExpenseListItem.CategorySummaryItem> {
        // When grouping by category, we need to aggregate the data.
        return expenses
            .groupBy { it.category }
            .map { (category, expensesInCategory) ->
                ExpenseListItem.CategorySummaryItem(
                    category = category,
                    totalAmount = expensesInCategory.sumOf { it.amount },
                    expenseCount = expensesInCategory.size
                )
            }
        //.sortedByDescending { it.totalAmount }
    }

    private fun calculateDateRange(dateFilter: DateFilterType): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        return when (dateFilter) {
            DateFilterType.TODAY -> {
                val startOfDay = getStartOfDayInMillis(calendar)
                val endOfDay = calendar.apply {
                    set(Calendar.HOUR_OF_DAY, 23)
                    set(Calendar.MINUTE, 59)
                    set(Calendar.SECOND, 59)
                    set(Calendar.MILLISECOND, 999)
                }.timeInMillis
                Pair(startOfDay, endOfDay)
            }

            DateFilterType.LAST_7_DAYS -> {
                val endDate = calendar.apply {
                    set(Calendar.HOUR_OF_DAY, 23)
                    set(Calendar.MINUTE, 59)
                    set(Calendar.SECOND, 59)
                    set(Calendar.MILLISECOND, 999)
                }.timeInMillis

                calendar.add(Calendar.DAY_OF_YEAR, -6)
                val startDate = getStartOfDayInMillis(calendar)

                Pair(startDate, endDate)
            }
            DateFilterType.PERIOD -> {
                val rawStart = _uiState.value.periodStartDate ?: 0L
                val rawEnd = _uiState.value.periodEndDate ?: 0L

                val calendar = Calendar.getInstance()

                calendar.timeInMillis = rawStart
                val startDate = calendar.apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis

                calendar.timeInMillis = rawEnd
                val endDate = calendar.apply {
                    set(Calendar.HOUR_OF_DAY, 23)
                    set(Calendar.MINUTE, 59)
                    set(Calendar.SECOND, 59)
                    set(Calendar.MILLISECOND, 999)
                }.timeInMillis

                Pair(startDate, endDate)
            }
        }
    }

    private fun getStartOfDayInMillis(calendar: Calendar): Long {
        return calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    fun onDateFilterChanged(newFilter: DateFilterType) {
        _uiState.update { it.copy(selectedDateFilter = newFilter) }
    }

    fun onDateFilterChangedByDisplayName(displayName: String) {
        val newFilter = DateFilterType.entries.find { it.displayName == displayName }

        newFilter?.let {
            onDateFilterChanged(it)
        }
    }

    fun onGroupByChanged(selectedName: String) {
        val newGroupBy = GroupingType.entries.find { it.displayName == selectedName }

        newGroupBy?.let {
            _uiState.update { currentState ->
                currentState.copy(selectedGroupBy = it)
            }
        }
    }

    fun onPeriodStartDateChanged(startDate: Long) {
        _uiState.update { it.copy(periodStartDate = startDate) }
    }

    fun onPeriodEndDateChanged(endDate: Long) {
        _uiState.update { it.copy(periodEndDate = endDate) }
    }
}