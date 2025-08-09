package com.example.expensetracker.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.designsystem.theme.LocalTypography
import com.example.expensetracker.designsystem.theme.OffWhite
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ExpenseCard(modifier: Modifier = Modifier, expense: Double) {
    val formatter = remember {
        NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    }
    val formattedExpense = formatter.format(expense)


    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = OffWhite,
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondary)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Your Total Expense",
                style = LocalTypography.current.label,
                fontSize = 18.sp,
            )
            Text(
                formattedExpense,
                style = LocalTypography.current.headingLarge,
                fontSize = 26.sp
            )
        }

    }
}