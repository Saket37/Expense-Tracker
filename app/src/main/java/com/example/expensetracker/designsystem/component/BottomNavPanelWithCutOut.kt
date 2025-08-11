package com.example.expensetracker.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.expensetracker.R
import com.example.expensetracker.designsystem.theme.LightGray

@Composable
fun BoxScope.BottomNavPanelWithCutOut(
    navigateToHome: () -> Unit,
    navigateToReport: () -> Unit,
    selectedRoute: String
) {
    Box(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .height(64.dp)
            .clip(
                BottomNavShape(
                    cornerRadius = with(LocalDensity.current) { 0.dp.toPx() },
                    dockRadius = with(LocalDensity.current) { 38.dp.toPx() },
                ),
            )
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clickable { navigateToHome() },
                contentAlignment = Alignment.Center
            ) {
                if (selectedRoute == "home") {
                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .width(64.dp)
                            .clip(RoundedCornerShape(32.dp))
                            .background(LightGray)
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.ic_home_icon),
                    modifier = Modifier.size(24.dp),
                    contentDescription = "Home"
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clickable { navigateToReport() },
                contentAlignment = Alignment.Center
            ) {
                if (selectedRoute == "report") {
                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .width(64.dp)
                            .clip(RoundedCornerShape(32.dp))
                            .background(LightGray)
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.ic_report_icon),
                    modifier = Modifier.size(24.dp),
                    contentDescription = "Report"
                )
            }


        }
    }
}

@Composable
fun BoxScope.BottomNavPanel(
    navController: NavController,
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    Box(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
    ) {
        BottomNavPanelWithCutOut(
            navigateToHome = {
                if (currentDestination != "home") {
                    navController.popBackStack()
                }
            },
            navigateToReport = {
                if (currentDestination != "report") {
                    navController.navigate("report") {
                        popUpTo("home") { inclusive = false }
                        launchSingleTop = true
                    }
                }

            },
            selectedRoute = currentDestination.toString()
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .size(58.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    navController.navigate("add") {
                        popUpTo("home") { inclusive = false }
                        launchSingleTop = true
                    }
                },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                modifier = Modifier.size(36.dp),
                contentDescription = null
            )
        }
    }
}