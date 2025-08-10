package com.example.expensetracker.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

@Composable
fun GroupRow(
    modifier: Modifier = Modifier,
    type: List<String>,
    isSelected: String,
    onClick: (String) -> Unit
) {
    Row {
        Box(
            modifier = Modifier
                .background(
                    color = if (isSelected == type[0])
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSecondaryContainer,
                    shape = RoundedCornerShape(topStart = 32.dp, bottomStart = 32.dp)
                )
                .clickable {
                    onClick(type[0])
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                type[0],
                color = if (isSelected == type[0])
                    MaterialTheme.colorScheme.surfaceContainerHighest
                else
                    MaterialTheme.colorScheme.surfaceContainerLowest,
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp),
                fontSize = 12.sp

            )
        }
        Box(
            modifier = Modifier
                .background(
                    color = if (isSelected == type[1])
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSecondaryContainer,
                    shape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp)
                )
                .clickable {
                    onClick(type[1])
                },
            contentAlignment = Alignment.Center,

            ) {
            Text(
                type[1],
                color = if (isSelected == type[1])
                    MaterialTheme.colorScheme.surfaceContainerHighest
                else
                    MaterialTheme.colorScheme.surfaceContainerLowest,
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp),
                fontSize = 12.sp
            )
        }
    }

}
