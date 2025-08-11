package com.example.expensetracker.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.designsystem.component.AppScaffold
import com.example.expensetracker.designsystem.component.EmptyStateUI
import com.example.expensetracker.designsystem.component.ExpenseCard
import com.example.expensetracker.designsystem.component.ExpenseCategoryItem
import com.example.expensetracker.designsystem.component.ExpenseItem
import com.example.expensetracker.designsystem.component.GroupRow
import com.example.expensetracker.designsystem.component.SegmentedControl
import com.example.expensetracker.designsystem.theme.LightGreen
import com.example.expensetracker.designsystem.theme.LocalTypography
import com.example.expensetracker.designsystem.theme.OffWhite
import com.example.expensetracker.domain.models.AppTheme
import com.example.expensetracker.domain.models.DateFilterType
import com.example.expensetracker.domain.models.GroupingType
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeScreenViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        navController = navController,
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    uiState: ExpenseListUiState,
    onEvent: (HomeScreenEvent) -> Unit,
) {
    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value == true) {
        ThemeSelectionDialog(
            onDismissRequest = { showDialog.value = false }
        ) {

        }
    }
    AppScaffold(onThemeIconClick = {
        showDialog.value = true
    }, navController = navController, showAppBar = true) {
        //var selectedTab by remember { mutableStateOf("Today") }
        val tabs = DateFilterType.entries.map { it.displayName }

        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            SegmentedControl(
                tabs = tabs,
                selectedTab = uiState.selectedDateFilter.displayName,
                onTabSelected = {
                    onEvent(HomeScreenEvent.FilterSelected(it))
                }
            )
            if (uiState.selectedDateFilter == DateFilterType.PERIOD) {
                DateRangeSelector(
                    startDate = uiState.periodStartDate,
                    endDate = uiState.periodEndDate,
                    onEvent = onEvent
                )
            }

            val shouldShowExpenseList = uiState.selectedDateFilter != DateFilterType.PERIOD ||
                    (uiState.periodStartDate != null && uiState.periodEndDate != null)

            if (shouldShowExpenseList) {
                ExpenseListContent(onEvent = onEvent, uiState = uiState)
            }
        }
    }
}

@Composable
fun ExpenseListContent(
    modifier: Modifier = Modifier,
    onEvent: (HomeScreenEvent) -> Unit,
    uiState: ExpenseListUiState
) {
    val lazyState = rememberLazyListState()

    LazyColumn(
        state = lazyState, contentPadding = PaddingValues(
            top = 12.dp, bottom = 32.dp, start = 16.dp, end = 16.dp
        ),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // TODO update while its loading
        item {
            ExpenseCard(expense = uiState.totalAmount)
        }
        item {
            if (uiState.isLoading) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    CircularProgressIndicator()
                }
            } else if (!uiState.isLoading && uiState.displayItems.isEmpty()) {
                EmptyStateUI()
            }
        }
        item {
            if (uiState.displayItems.isEmpty().not())
                ExpenseTextRow(
                    selected = uiState.selectedGroupBy.displayName,
                    totalCount = uiState.totalCount,
                    onSelectGroup = {
                        onEvent(HomeScreenEvent.GroupingChanged(it))
                    }
                )

        }
        itemsIndexed(items = uiState.displayItems, key = { index, item ->
            when (item) {
                is ExpenseListItem.ExpenseItem -> "expense-${item.id}"
                is ExpenseListItem.CategorySummaryItem -> "category-${item.category.name}"
            }
        }) { index, item ->
            when (item) {
                is ExpenseListItem.ExpenseItem -> {
                    ExpenseItem(
                        title = item.description,
                        category = item.category.displayName,
                        notes = item.description,
                        expense = item.amount,
                        time = item.date
                    )
                }

                is ExpenseListItem.CategorySummaryItem -> {
                    ExpenseCategoryItem(
                        title = item.category.displayName,
                        expense = item.totalAmount
                    )
                }
            }
        }
    }
}

@Composable
fun DateRangeSelector(
    startDate: Long?,
    endDate: Long?,
    onEvent: (HomeScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    // State for managing which picker is open (for start or end date)
    var showDatePicker by remember { mutableStateOf(false) }
    val formatter = remember {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    }
    val dateText = if (startDate != null && endDate != null) {
        val formattedStart = formatter.format(Date(startDate))
        val formattedEnd = formatter.format(Date(endDate))
        "$formattedStart - $formattedEnd"
    } else {
        "Select a date range"
    }
    val datePickerState = rememberDateRangePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    enabled = datePickerState.selectedStartDateMillis != null
                            && datePickerState.selectedEndDateMillis != null,
                    colors = ButtonDefaults.buttonColors(contentColor = Color.Black),
                    onClick = {
                        showDatePicker = false
                        datePickerState.selectedStartDateMillis?.let {
                            onEvent(HomeScreenEvent.PeriodStartDateSelected(it))
                        }
                        datePickerState.selectedEndDateMillis?.let {
                            onEvent(HomeScreenEvent.PeriodEndDateSelected(it))
                        }
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    colors = ButtonDefaults.buttonColors(contentColor = Color.Black),
                    onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            },

            ) {
            DateRangePicker(
                showModeToggle = false,
                state = datePickerState, title = {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Select date range to view your expense",
                            style = LocalTypography.current.headingSmall,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.surfaceContainerLowest

                        )
                    }
                },
                headline = {
                    val formatter = remember { SimpleDateFormat("dd MMM", Locale.getDefault()) }
                    val startDate =
                        datePickerState.selectedStartDateMillis?.let { formatter.format(Date(it)) }
                            ?: "Start Date"
                    val endDate =
                        datePickerState.selectedEndDateMillis?.let { formatter.format(Date(it)) }
                            ?: "End Date"
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$startDate - $endDate",
                            style = LocalTypography.current.label,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                            color = MaterialTheme.colorScheme.surfaceContainerLowest
                        )
                    }
                },
                colors = DatePickerDefaults.colors(
                    selectedDayContentColor = OffWhite,
                    dayInSelectionRangeContentColor = Color.Black,
                    dayContentColor = MaterialTheme.colorScheme.onBackground,
                    dayInSelectionRangeContainerColor = LightGreen,
                    weekdayContentColor = MaterialTheme.colorScheme.primary,
                    subheadContentColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                    headlineContentColor = MaterialTheme.colorScheme.surfaceContainerLowest
                )
            )
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = dateText,
            style = LocalTypography.current.label,
            fontSize = 16.sp
        )
        Spacer(Modifier.width(16.dp))
        Icon(
            imageVector = Icons.Default.CalendarToday,
            contentDescription = "Select Date Range",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                showDatePicker = true
            }
        )
    }
}

@Composable
fun ExpenseTextRow(
    modifier: Modifier = Modifier,
    selected: String,
    totalCount: Int,
    onSelectGroup: (String) -> Unit
) {
    val annotatedText = buildAnnotatedString {
        append("Expenses")
        if (selected == GroupingType.BY_TIME.displayName) {
            append(" ($totalCount)")
        }
    }
    //var selected by remember { mutableStateOf("Time") }
    val type = listOf("Time", "Category")
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            annotatedText,
            style = LocalTypography.current.headingSmall,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.surfaceContainerLowest
        )

        GroupRow(
            type = type,
            isSelected = selected
        ) {
            onSelectGroup(it)
        }
    }
}


// TODO update it to show like zomato ver option ui

@Composable
fun ThemeSelectionDialog(
    onDismissRequest: () -> Unit,
    onThemeSelected: (AppTheme) -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = RectangleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Choose theme",
                    style = LocalTypography.current.headingLarge,
                    modifier = Modifier.padding(bottom = 20.dp),
                    fontSize = 24.sp
                )

                ThemeOption(
                    icon = R.drawable.ic_light_theme,
                    label = "Light",
                    onClick = { onThemeSelected(AppTheme.LIGHT) }
                )
                ThemeOption(
                    icon = R.drawable.ic_system_theme,
                    label = "System",
                    onClick = { onThemeSelected(AppTheme.SYSTEM) }
                )
                ThemeOption(
                    icon = R.drawable.ic_dark_theme,
                    label = "Dark",
                    onClick = { onThemeSelected(AppTheme.DARK) }
                )


            }
        }
    }
}


@Composable
private fun ThemeOption(
    @DrawableRes icon: Int,
    label: String,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "$label Theme",
                modifier = Modifier.size(32.dp),
            )
        }
        Text(
            text = label,
            style = LocalTypography.current.label,
            fontSize = 12.sp
        )
    }
}