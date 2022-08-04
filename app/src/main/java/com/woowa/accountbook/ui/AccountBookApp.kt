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
                AccountBookBottomBar(
                    tabs = appState.bottomBarTabs,
                    currentRoute = appState.currentRoute ?: History.route,
                    navigateToRoute = { route -> appState.navigateToBottomBarRoute(route) }
                )
            },
            floatingActionButton = {
                if (appState.shouldShowFloatingActionButton) {
                    AccountBookFloatingButton(
                        icon = IconPack.Plus,
                        onClicked = { appState.navigateToRegistration(Registration.route) }
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
                startDestination = "home",
                modifier = Modifier.padding(innerPaddingModifier)
            ) {
                accountBookNavGraph(
                    navController = appState.navController,
                    snackbarHostState = appState.scaffoldState.snackbarHostState,
                    navigationUp = { appState.navigateUp() },
                    onClickedDetail = {
                        appState.navigateToStatisticsDetail(StatisticsDetail.route, it)
                    },
                    onClicked = { id ->
                        appState.navigateToRegistration(Registration.route, id)
                    },
                    onSectionItemClicked = { id, type ->
                        appState.navigateToSettingRegistration(
                            SettingRegistration.route,
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
    tabs: List<HomeSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
) {
    val currentSection = tabs.firstOrNull { it.route == currentRoute }

    BottomNavigation {
        tabs.forEach { section ->
            val selected =
                section == currentSection || selectNavigation(section.route, currentRoute)
            BottomNavigationItem(
                icon = {
                    Icon(imageVector = section.icon, contentDescription = section.route)
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

fun selectNavigation(tab: String, currentRoute: String): Boolean {
    return currentRoute.contains(tab)
}

private fun NavGraphBuilder.accountBookNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    onClicked: (Int) -> Unit,
    onClickedDetail: (Int?) -> Unit,
    onSectionItemClicked: (Int?, String) -> Unit,
    navigationUp: () -> Unit,
    historyViewModel: HistoryViewModel,
    calendarViewModel: CalendarViewModel,
    settingViewModel: SettingViewModel
) {
    navigation(
        route = "home",
        startDestination = History.route
    ) {
        addHomeGraph(
            historyViewModel,
            calendarViewModel,
            settingViewModel,
            snackbarHostState,
            onClicked = { id -> onClicked(id) },
            onSectionItemClicked = { id, type -> onSectionItemClicked(id, type) },
            onClickedDetail = { onClickedDetail(it) }
        )
    }
    composable(
        route = "${Registration.route}/{id}",
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
    composable(route = "${StatisticsDetail.route}/{id}",
        arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )
    ) { entry ->
        val id = entry.arguments?.getInt("id") ?: -1
        StatisticsDetailScreen(
            id,
            historyViewModel,
            calendarViewModel,
            navigationUp = navigationUp
        )
    }

    composable(route = "${SettingRegistration.route}/{id}/{type}",
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
    onSectionItemClicked: (Int?, String) -> Unit,
    onClickedDetail: (Int?) -> Unit
) {
    composable(History.route) {
        HistoryScreen(
            historyViewModel,
            calendarViewModel,
            onClicked = { id -> onClicked(id) }
        )
    }
    composable(Calendar.route) {
        CalendarScreen(historyViewModel, calendarViewModel)
    }
    composable(Statistics.route) {
        StatisticsScreen(calendarViewModel, onClickedDetail = { onClickedDetail(it) })
    }
    composable(Setting.route) {
        SettingScreen(
            settingViewModel,
            snackbarHostState,
            onSectionItemClicked = { id, type -> onSectionItemClicked(id, type) }
        )
    }
}