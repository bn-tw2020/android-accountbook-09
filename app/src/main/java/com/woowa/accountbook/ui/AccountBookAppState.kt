package com.woowa.accountbook.ui

import androidx.annotation.StringRes
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.woowa.accountbook.R
import com.woowa.accountbook.ui.iconpack.*

enum class HomeSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    HISTORY(R.string.history, IconPack.History, "home/history"),
    CALENDAR(R.string.calendar, IconPack.Calendar, "home/calendar"),
    STATISTICS(R.string.statistics, IconPack.Statistics, "home/statistics"),
    SETTING(R.string.setting, IconPack.Setting, "home/setting")
}

object Destinations {
    const val REGISTRATION = "home/history/registration"
    const val HOME = "home"
    const val STATISTICS_DETAIL = "home/statistics/detail"
}

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
    val bottomBarTabs = HomeSections.values()
    private val bottomBarRoutes = bottomBarTabs.map { it.route }
        .plus(listOf(Destinations.REGISTRATION, Destinations.STATISTICS_DETAIL))

    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes


    val shouldShowFloatingActionButton: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route == HomeSections.HISTORY.route

    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    fun navigateToRegistration(route: String, id: Int = -1) {
        if (route != currentRoute) {
            navController.navigate("${route}/${id}")
        }
    }
}