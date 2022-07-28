package com.woowa.accountbook.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.woowa.accountbook.ui.calendar.component.CalendarScreen
import com.woowa.accountbook.ui.component.AccountBookFloatingButton
import com.woowa.accountbook.ui.history.component.HistoryScreen
import com.woowa.accountbook.ui.history.component.RegistrationScreen
import com.woowa.accountbook.ui.iconpack.IconPack
import com.woowa.accountbook.ui.iconpack.Plus
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
            NavHost(
                navController = appState.navController,
                startDestination = Destinations.HOME,
                modifier = Modifier.padding(innerPaddingModifier)
            ) {
                accountBookNavGraph(
                    navController = appState.navController,
                    navigationUp = { appState.navigateUp() }
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
        else -> false
    }
}

private fun NavGraphBuilder.accountBookNavGraph(
    navController: NavController,
    navigationUp: () -> Unit
) {
    navigation(
        route = Destinations.HOME,
        startDestination = HomeSections.HISTORY.route
    ) {
        addHomeGraph()
    }
    composable(route = Destinations.REGISTRATION) {
        RegistrationScreen()
    }
    composable(route = Destinations.STATISTICS_DETAIL) {
        StatisticsDetailScreen()
    }
}

private fun NavGraphBuilder.addHomeGraph() {
    composable(HomeSections.HISTORY.route) {
        HistoryScreen()
    }
    composable(HomeSections.CALENDAR.route) {
        CalendarScreen()
    }
    composable(HomeSections.STATISTICS.route) {
        StatisticsScreen()
    }
    composable(HomeSections.SETTING.route) {
        SettingScreen()
    }
}