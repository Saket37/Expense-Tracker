package com.example.expensetracker.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.expensetracker.ui.home.HomeScreenRoot
import com.example.expensetracker.ui.report.ReportScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = "app") {
        appNavGraph(navController)
    }

}

private fun NavGraphBuilder.appNavGraph(
    navController: NavController
) {
    navigation(startDestination = "home", route = "app") {
        composable(route = "home") {
            HomeScreenRoot(navController = navController)
        }
        composable(route = "report") {
            ReportScreenRoot(navController = navController)
        }
        composable(route = "add") {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Add")
            }
        }

    }
}