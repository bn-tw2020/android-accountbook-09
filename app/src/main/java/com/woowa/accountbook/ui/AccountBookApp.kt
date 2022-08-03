package com.woowa.accountbook.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.woowa.accountbook.ui.calendar.CalendarViewModel
import com.woowa.accountbook.ui.calendar.component.CalendarScreen
import com.woowa.accountbook.ui.component.AccountBookFloatingButton
import com.woowa.accountbook.ui.history.HistoryViewModel
import com.woowa.accountbook.ui.history.component.HistoryScreen
import com.woowa.accountbook.ui.history.component.RegistrationScreen
import com.woowa.accountbook.ui.iconpack.IconPack
import com.woowa.accountbook.ui.iconpack.Plus
import com.woowa.accountbook.ui.settings.SettingViewModel
import com.woowa.accountbook.ui.settings.component.RegistrationSectionScreen
import com.woowa.accountbook.ui.settings.component.SettingScreen
import com.woowa.accountbook.ui.statistics.component.StatisticsDetailScreen
import com.woowa.accountbook.ui.statistics.component.StatisticsScreen
import com.woowa.accountbook.ui.theme.AccountBookTheme
import com.woowa.accountbook.ui.theme.White
import com.woowa.accountbook.ui.theme.White50

@Composable
fun AccountBookApp() {
    AccountBookTheme {
        val appState = rememberAccountBookState()
        Scaffold(
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    AccountBookBottomBar(
                        tabs = appState.bottomBarTabs,
                        currentRoute = appState.currentRoute ?: HomeSections.HISTORY.route,
                        navigateToRoute = { route -> appState.navigateToBottomBarRoute(route) }
                    )
                }
            },
            floatingActionButton = {
                if (appState.shouldShowFloatingActionButton) {
                    AccountBookFloatingButton(
                        icon = IconPack.Plus,
                        onClicked = { appState.navigateToRegistration(Destinations.REGISTRATION) }
                    )
                }
            },
            scaffoldState = appState.scaffoldState
        ) { innerPaddingModifier ->

            val historyViewModel: HistoryViewModel = hiltViewModel()
            val calendarViewModel: CalendarViewModel = hiltViewModel()
            val settingViewModel: SettingViewModel = hiltViewModel()

            NavHost(
                navController = appState.navController,
                startDestination = Destinations.HOME,
                modifier = Modifier.padding(innerPaddingModifier)
            ) {
                accountBookNavGraph(
                    navController = appState.navController,
                    snackbarHostState = appState.scaffoldState.snackbarHostState,
                    navigationUp = { appState.navigateUp() },
                    onClicked = { id ->
                        appState.navigateToRegistration(Destinations.REGISTRATION, id)
                    },
                    onSectionItemClicked = { id, type ->
                        appState.navigateToSettingRegistration(
                            Destinations.SETTING_REGISTRATION,
                            id,
                            type
                        )
                    },
                    historyViewModel = historyViewModel,
                    calendarViewModel = calendarViewModel,
                    settingViewModel = settingViewModel,
                )
            }
        }
    }
}

@Composable
fun AccountBookBottomBar(
    tabs: Array<HomeSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
) {
    val currentSection = tabs.firstOrNull { it.route == currentRoute }

    BottomNavigation {
        tabs.forEach { section ->
            val selected = section == currentSection || selectNavigation(currentRoute, section)
            BottomNavigationItem(
                icon = {
                    Icon(imageVector = section.icon, contentDescription = section.name)
                },
                label = {
                    Text(text = stringResource(id = section.title))
                },
                alwaysShowLabel = true,
                selectedContentColor = White,
                unselectedContentColor = White50,
                onClick = { navigateToRoute(section.route) },
                selected = selected,
            )
        }
    }
}

fun selectNavigation(currentRoute: String, section: HomeSections): Boolean {
    return when (section.route) {
        HomeSections.HISTORY.route -> currentRoute.contains(HomeSections.HISTORY.route)
        HomeSections.SETTING.route -> currentRoute.contains(HomeSections.SETTING.route)
        else -> false
    }
}

private fun NavGraphBuilder.accountBookNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    onClicked: (Int) -> Unit,
    onSectionItemClicked: (Int?, String) -> Unit,
    navigationUp: () -> Unit,
    historyViewModel: HistoryViewModel,
    calendarViewModel: CalendarViewModel,
    settingViewModel: SettingViewModel
) {
    navigation(
        route = Destinations.HOME,
        startDestination = HomeSections.HISTORY.route
    ) {
        addHomeGraph(
            historyViewModel,
            calendarViewModel,
            settingViewModel,
            snackbarHostState,
            onClicked = { id -> onClicked(id) },
            onSectionItemClicked = { id, type -> onSectionItemClicked(id, type) }
        )
    }
    composable(
        route = "${Destinations.REGISTRATION}/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )
    ) { entry ->
        val id = entry.arguments?.getInt("id") ?: -1
        RegistrationScreen(
            id,
            historyViewModel,
            calendarViewModel,
            navigationUp = navigationUp,
            onSectionItemClicked
        )
    }
    composable(route = Destinations.STATISTICS_DETAIL) {
        StatisticsDetailScreen()
    }

    composable(route = "${Destinations.SETTING_REGISTRATION}/{id}/{type}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            },
            navArgument("type") {
                type = NavType.StringType
            }
        )
    ) { entry ->
        val id = entry.arguments?.getInt("id") ?: -1
        val type = entry.arguments?.getString("type")
        RegistrationSectionScreen(
            id = id,
            type = type,
            navigationUp = navigationUp,
            settingViewModel = settingViewModel
        )
    }

}

private fun NavGraphBuilder.addHomeGraph(
    historyViewModel: HistoryViewModel,
    calendarViewModel: CalendarViewModel,
    settingViewModel: SettingViewModel,
    snackbarHostState: SnackbarHostState,
    onClicked: (Int) -> Unit,
    onSectionItemClicked: (Int?, String) -> Unit
) {
    composable(HomeSections.HISTORY.route) {
        HistoryScreen(
            historyViewModel,
            calendarViewModel,
            onClicked = { id -> onClicked(id) }
        )
    }
    composable(HomeSections.CALENDAR.route) {
        CalendarScreen(historyViewModel, calendarViewModel)
    }
    composable(HomeSections.STATISTICS.route) {
        StatisticsScreen(calendarViewModel)
    }
    composable(HomeSections.SETTING.route) {
        SettingScreen(
            settingViewModel,
            snackbarHostState,
            onSectionItemClicked = { id, type -> onSectionItemClicked(id, type) }
        )
    }
}