package com.example.expensetracker.ui.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetracker.designsystem.component.AppScaffold
import com.example.expensetracker.designsystem.theme.LocalTypography

@Composable
fun ReportScreenRoot(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    ReportScreen(
        navController = navController
    )
}

@Composable
private fun ReportScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    AppScaffold(onThemeIconClick = {
    }, navController = navController, showAppBar = false) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Text("Report", style = LocalTypography.current.headingLarge, fontSize = 32.sp)
        }


    }
}