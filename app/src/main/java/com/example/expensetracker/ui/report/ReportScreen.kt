package com.example.expensetracker.ui.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.expensetracker.designsystem.component.AppScaffold
import com.example.expensetracker.designsystem.component.BarGraphItem
import com.example.expensetracker.designsystem.component.ExpenseCategoryItem
import com.example.expensetracker.designsystem.theme.LocalTypography
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ReportScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ReportScreenViewModel = koinViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    ReportScreen(
        navController = navController,
        uiState = uiState
    )
}

@Composable
private fun ReportScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    uiState: ExpenseReportState
) {
    val formatter = remember {
        NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    }
    val formattedExpense = formatter.format(uiState.grandTotal)
    AppScaffold(onThemeIconClick = {
    }, navController = navController, showAppBar = false) {
        val listState = rememberLazyListState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Last 7 days Report",
                style = LocalTypography.current.headingLarge,
                fontSize = 32.sp
            )

            Text(
                "Your Total expense : $formattedExpense",
                style = LocalTypography.current.headingSmall,
                fontSize = 18.sp
            )

            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = 32.dp
                ),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item {
                    Text(
                        "Daily Total Spent",
                        style = LocalTypography.current.headingSmall,
                        fontSize = 16.sp
                    )
                }
                item {
                    BarGraphItem(
                        barDetails = uiState.barDetails,
                    )
                }

                item {
                    Text(
                        "Category-wise Total",
                        style = LocalTypography.current.headingSmall,
                        fontSize = 16.sp
                    )
                }

                itemsIndexed(items = uiState.categoryTotals) { index, item ->
                    ExpenseCategoryItem(
                        title = item.category.displayName,
                        expense = item.total
                    )
                }


            }
        }


    }
}
