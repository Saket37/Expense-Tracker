package com.example.expensetracker.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.example.expensetracker.designsystem.theme.ExpenseTrackerTypography
import com.example.expensetracker.designsystem.theme.LocalTypography

@Composable
fun ComposeLocalWrapper(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalTypography provides remember { ExpenseTrackerTypography }
    ) {
        content
    }

}