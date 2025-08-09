package com.example.expensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.expensetracker.ui.home.HomeScreenRoot

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
            HomeScreenRoot()
        }
//        composable(route = "report") { }
//        composable(route = "add") { }

    }
}