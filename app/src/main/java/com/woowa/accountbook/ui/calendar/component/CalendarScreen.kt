package com.woowa.accountbook.ui.calendar.component

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowa.accountbook.common.rawToMoneyFormat
import com.woowa.accountbook.domain.entity.DateItem
import com.woowa.accountbook.ui.calendar.CalendarViewModel
import com.woowa.accountbook.ui.component.AccountBookAppBar
import com.woowa.accountbook.ui.component.AccountBookDialog
import com.woowa.accountbook.ui.component.BothSideText
import com.woowa.accountbook.ui.component.YearAndMonthPicker
import com.woowa.accountbook.ui.history.HistoryViewModel
import com.woowa.accountbook.ui.iconpack.IconPack
import com.woowa.accountbook.ui.iconpack.LeftArrow
import com.woowa.accountbook.ui.iconpack.RightArrow
import com.woowa.accountbook.ui.theme.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(
    historyViewModel: HistoryViewModel = hiltViewModel(),
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    val yearAndMonth = calendarViewModel.yearAndMonth.collectAsState().value
    val (year, month) = calendarViewModel.yearMonthPair.value
    historyViewModel.initHistory(month)
    val histories = historyViewModel.totalHistory.collectAsState()
    calendarViewModel.getAllDate(histories.value)
    val dateList = calendarViewModel.dateList.collectAsState().value

    Scaffold(
        backgroundColor = OffWhite,
        topBar = {
            AccountBookAppBar(
                title = yearAndMonth,
                navigationIcon = IconPack.LeftArrow,
                onNavigationClicked = {
                    calendarViewModel.previousYearAndMonth()
                },
                actionIcon = IconPack.RightArrow,
                onActionClicked = {
                    calendarViewModel.nextYearAndMonth()
                },
                dialog = { isShow, onDismissRequest ->
                    AccountBookDialog(
                        isShow = isShow,
                        onDismissRequest = { onDismissRequest(it) },
                        modifier = Modifier
                            .fillMaxWidth(),
                        content = {
                            YearAndMonthPicker(
                                year,
                                month,
                                onClicked = { year, month ->
                                    onDismissRequest(it)
                                    calendarViewModel.setYearAndMonth(year, month)
                                }
                            )
                        }
                    )
                }
            )
        }
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(LightPurple)
        )
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            val selectedDate = calendarViewModel.selectedDate.collectAsState().value
            HistoryCalendar(
                dateList = dateList,
                onCheckToday = { if (calendarViewModel.isToday(it)) White else OffWhite },
                onClicked = { calendarViewModel.selectedDate(it) },
                selectedDate = selectedDate
            )
            Spacer(modifier = Modifier.height(16.dp))
            val totalViewModel = historyViewModel.totalHistory.collectAsState().value
            val monthTotalIncome =
                totalViewModel.filter { it.category?.isIncome == 1 }.sumOf { it.money }
            val monthTotalExpense =
                totalViewModel.filter { it.category?.isIncome == 0 }.sumOf { it.money }
            val totalIncome = rawToMoneyFormat(monthTotalIncome, 1)
            val totalExpense = rawToMoneyFormat(monthTotalExpense, 0)
            val total = rawToMoneyFormat(monthTotalIncome - monthTotalExpense, 1)
            MonthIncomeAndExpense(totalIncome, totalExpense, total)
        }
    }

}

@Composable
private fun MonthIncomeAndExpense(
    totalIncome: String,
    totalExpense: String,
    total: String
) {
    BothSideText(
        leftText = {
            Text(
                text = "수입",
                style = MaterialTheme.typography.subtitle2,
                color = Purple,
            )
        },
        rightText = {
            Text(
                text = if (totalIncome != "") totalIncome else "0",
                style = MaterialTheme.typography.subtitle2,
                color = Green3
            )
        }
    )
    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Purple40)
    BothSideText(
        leftText = {
            Text(
                text = "지출",
                style = MaterialTheme.typography.subtitle2,
                color = Purple,
            )
        },
        rightText = {
            Text(
                text = if (totalExpense != "") totalExpense else "0",
                style = MaterialTheme.typography.subtitle2,
                color = Red
            )
        }
    )
    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Purple40)
    BothSideText(
        leftText = {
            Text(
                text = "총합",
                style = MaterialTheme.typography.subtitle2,
                color = Purple,
            )
        },
        rightText = {
            Text(
                text = if (total != "") total else "0",
                style = MaterialTheme.typography.subtitle2,
                color = Purple
            )
        }
    )
    Spacer(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(LightPurple)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HistoryCalendar(
    dateList: List<DateItem>,
    onCheckToday: (DateItem) -> Color,
    onClicked: (DateItem) -> Unit,
    selectedDate: DateItem?
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .height((((dateList.size / 7) * 100) + ((dateList.size / 7) - 1) + 1).dp),
        cells = GridCells.Fixed(count = 7)
    ) {
        itemsIndexed(dateList) { _, dateItem ->
            CalendarItem(
                dateItem = dateItem,
                onCheckToday = { onCheckToday(it) },
                onClicked = { onClicked(it) },
                selectedDate = selectedDate
            )
        }
    }
}


@Composable
fun CalendarItem(
    dateItem: DateItem,
    onCheckToday: (DateItem) -> Color,
    onClicked: (DateItem) -> Unit,
    selectedDate: DateItem?
) {
    var color = onCheckToday(dateItem)
    if (selectedDate == dateItem) color = Purple40

    Column(
        modifier = Modifier
            .height(100.dp)
            .background(color)
            .border(
                width = 0.5.dp,
                color = LightPurple,
                shape = RectangleShape
            )
            .padding(2.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onClicked(dateItem) },
                indication = null
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val textStyle = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 7.sp,
            fontWeight = FontWeight.W700,
            lineHeight = 12.sp,
            letterSpacing = 0.4.sp
        )
        val income = dateItem.income ?: 0
        val expense = dateItem.expense ?: 0
        Column {
            if (income != 0) {
                Text(
                    text = rawToMoneyFormat(income, 1),
                    color = Green3,
                    style = textStyle
                )
            }
            if (expense != 0) {
                Text(
                    text = rawToMoneyFormat(expense, 0),
                    color = Red,
                    style = textStyle
                )
            }
            Text(
                text = rawToMoneyFormat(income - expense, 1),
                color = LightPurple,
                style = textStyle
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = dateItem.date.toString(),
            textAlign = TextAlign.End,
            color = dateItem.color,
            style = MaterialTheme.typography.caption
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    CalendarScreen()
}