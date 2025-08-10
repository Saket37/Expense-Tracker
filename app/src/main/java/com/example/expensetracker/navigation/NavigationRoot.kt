package com.example.expensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.expensetracker.ui.add.AddExpenseRoot
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
            AddExpenseRoot(onClose = {
                navController.popBackStack("home", inclusive = false)
            })
        }

    }
}