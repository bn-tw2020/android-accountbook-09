package com.woowa.accountbook.ui.statistics.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowa.accountbook.ui.calendar.CalendarViewModel
import com.woowa.accountbook.ui.component.AccountBookAppBar
import com.woowa.accountbook.ui.history.HistoryViewModel
import com.woowa.accountbook.ui.history.component.HistoryLazyColumn
import com.woowa.accountbook.ui.iconpack.IconPack
import com.woowa.accountbook.ui.iconpack.LeftArrow
import com.woowa.accountbook.ui.theme.LightPurple
import com.woowa.accountbook.ui.theme.OffWhite

@Composable
fun StatisticsDetailScreen(
    id: Int?,
    historyViewModel: HistoryViewModel = hiltViewModel(),
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    navigationUp: () -> Unit = {},
) {
    val (year, month) = calendarViewModel.yearMonthPair.value
    historyViewModel.getHistoryMonthAndType(month, false)
    val histories = historyViewModel.history.collectAsState().value
    val expenseHistory = histories.filter { it.category?.id == id }
    val groupHistory = expenseHistory.groupBy { it.day }

    Scaffold(
        backgroundColor = OffWhite,
        topBar = {
            AccountBookAppBar(
                title = "생활",
                navigationIcon = IconPack.LeftArrow,
                onNavigationClicked = { navigationUp() }
            )
        }
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(LightPurple)
        )
        Column {
            HistoryLazyColumn(groupHistory)
        }
    }
}