package com.example.expensetracker.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.R
import com.example.expensetracker.designsystem.theme.LocalTypography

@Composable
fun HomeTopAppBar(modifier: Modifier = Modifier, onThemeIconClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(
                start = 12.dp,
                end = 12.dp,
                top = 24.dp
            )
            .windowInsetsPadding(WindowInsets.statusBars),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Hello,",
                color = MaterialTheme.colorScheme.onBackground,
                style = LocalTypography.current.label,
                fontSize = 38.sp,
            )
            Text(
                text = "User",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 36.sp,
                style = LocalTypography.current.headingLarge
            )
        }

        IconButton(
            onClick = {
                onThemeIconClick()
            }, shape = CircleShape, modifier = Modifier
                .padding(4.dp)
                .background(MaterialTheme.colorScheme.tertiary, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_dark_theme),
                modifier = Modifier.size(32.dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }

    }
}


