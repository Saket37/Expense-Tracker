package com.example.expensetracker.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SegmentedControl(
    tabs: List<String>,
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = Modifier.padding(16.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
    ) {
        PrimaryTabRow(
            modifier = Modifier.height(42.dp),
            selectedTabIndex = tabs.indexOf(selectedTab),
            containerColor = Color.Transparent,
            divider = {},

            ) {
            tabs.forEach { tab ->
                Tab(
                    modifier = Modifier.background(
                        if (tab == selectedTab) MaterialTheme.colorScheme.primary
                        else Color.Transparent
                    ),
                    selected = tab == selectedTab,
                    onClick = { onTabSelected(tab) },
                    text = {
                        Text(
                            text = tab,
                            color = if (tab == selectedTab)
                                MaterialTheme.colorScheme.surfaceContainerHighest
                            else MaterialTheme.colorScheme.surfaceContainerLowest
                        )
                    },
                )
            }
        }
    }
}


