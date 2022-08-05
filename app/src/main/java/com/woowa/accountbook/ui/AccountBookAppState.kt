package com.woowa.accountbook.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAccountBookState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController()
) = remember(scaffoldState, navController) {
    AccountBookState(scaffoldState, navController)
}

class AccountBookState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController
) {
    val bottomBarTabs = listOf(History, Calendar, Statistics, Setting)
    private val bottomBarRoutes = bottomBarTabs.map { it.route }
        .plus(listOf(Registration.route, StatisticsDetail.route, SettingRegistration.route))

    val shouldShowFloatingActionButton: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route == History.route

    val currentRoute: String?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route

    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != navController.currentDestination?.route) {
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    fun navigateToRegistration(route: String, id: Int = -1) {
        if (route != navController.currentDestination?.route) {
            navController.navigate("${route}/${id}") {
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    fun navigateToStatisticsDetail(route: String, id: Int?) {
        if (route != navController.currentDestination?.route) {
            navController.navigate("${route}/${id}") {
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    fun navigateToSettingRegistration(route: String, id: Int?, type: String) {
        if (route != navController.currentDestination?.route) {
            navController.navigate("${route}/${id}/${type}") {
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}