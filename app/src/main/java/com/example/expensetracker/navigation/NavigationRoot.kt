package com.example.expensetracker.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
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
        composable(route = "report", enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(durationMillis = 400, easing = EaseInBounce)
            )
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(400, easing = EaseInOut)
            )
        }) {
            ReportScreenRoot(navController = navController)
        }
        composable(route = "add", exitTransition = {
            slideOutVertically() + fadeOut()
        }, enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                tween(durationMillis = 450, easing = EaseInBounce)
            )
        }) {
            AddExpenseRoot(onClose = {
                navController.popBackStack("home", inclusive = false)
            })
        }

    }
}