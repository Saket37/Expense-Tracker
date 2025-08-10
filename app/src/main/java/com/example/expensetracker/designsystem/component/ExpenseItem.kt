package com.example.expensetracker.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.designsystem.theme.LocalTypography
import com.example.expensetracker.utils.Utility.formatDateTime
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ExpenseItem(
    modifier: Modifier = Modifier,
    title: String,
    category: String,
    notes: String?,
    expense: Double,
    time: Long
) {
    var showNotes by remember { mutableStateOf(false) }
    val formatter = remember {
        NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    }
    val formattedExpense = formatter.format(expense)
    val formattedTime = remember(time) { formatDateTime(time) }

    Column {
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    title,
                    style = LocalTypography.current.body,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.surfaceContainerLowest
                )
                // TODO set color for category
                notes?.let {
                    Text(
                        text = if (showNotes) "Hide notes" else "Click to view notes",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clickable { showNotes = !showNotes }
                    )
                }

            }
            Text(
                category,
                style = LocalTypography.current.headingSmall,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.surfaceContainerLowest
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    formattedExpense,
                    style = LocalTypography.current.headingSmall,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.surfaceContainerLowest
                )
                Text(
                    formattedTime,
                    style = LocalTypography.current.body,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.surfaceContainerLowest
                )
            }
        }
        AnimatedVisibility(showNotes) {
            notes?.let {
                Text(
                    it,
                    style = LocalTypography.current.body,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.surfaceContainerLowest
                )
            }
        }

    }

}