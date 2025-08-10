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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.designsystem.theme.LocalTypography
import com.example.expensetracker.utils.Utility.formatDateTime
import java.text.NumberFormat
import java.util.Locale


// TODO set color for category and add icon
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

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    title,
                    style = LocalTypography.current.body,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.surfaceContainerLowest
                )
                if (notes != "null") {
                    Text(
                        text = if (showNotes) "Hide notes" else "Click to view notes",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { showNotes = !showNotes }
                    )
                }

            }
            Text(
                modifier = Modifier.weight(1f),
                text = category,
                style = LocalTypography.current.headingSmall,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.surfaceContainerLowest
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.surfaceContainerLowest
                )
            }
        }
        AnimatedVisibility(showNotes) {
            if (notes != "null") {
                Text(
                    notes.toString(),
                    style = LocalTypography.current.label,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.surfaceContainerLowest
                )
            }
        }

    }

}

@Composable
fun ExpenseCategoryItem(modifier: Modifier = Modifier, title: String, expense: Double) {
    val formatter = remember {
        NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    }
    val formattedExpense = formatter.format(expense)
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            title,
            style = LocalTypography.current.headingSmall,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.surfaceContainerLowest
        )
        Text(
            formattedExpense,
            style = LocalTypography.current.body,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    }
}