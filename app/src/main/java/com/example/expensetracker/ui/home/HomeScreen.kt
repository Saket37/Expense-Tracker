package com.example.expensetracker.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.designsystem.component.AppScaffold
import com.example.expensetracker.designsystem.component.EmptyStateUI
import com.example.expensetracker.designsystem.component.ExpenseCard
import com.example.expensetracker.designsystem.component.SegmentedControl
import com.example.expensetracker.designsystem.theme.LocalTypography
import com.example.expensetracker.domain.models.AppTheme
import com.example.expensetracker.domain.models.DateFilterType
import org.koin.androidx.compose.koinViewModel

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
    val lazyState = rememberLazyListState()
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

            LazyColumn(
                state = lazyState, contentPadding = PaddingValues(
                    top = 12.dp, bottom = 32.dp, start = 16.dp, end = 16.dp
                )
            ) {
                item {
                    ExpenseCard(expense = uiState.totalAmount)
                }
                item {
                    if (uiState.displayItems.isEmpty()) {
                        EmptyStateUI()
                    }
                }
                item {

                }
            }


        }
    }
}

@Composable
fun ExpenseTextRow(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Expenses",
            style = LocalTypography.current.headingSmall,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.surfaceContainerLowest
        )
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