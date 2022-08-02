package com.woowa.accountbook.ui.history.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowa.accountbook.common.rawToMoneyFormat
import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.ui.calendar.CalendarViewModel
import com.woowa.accountbook.ui.component.*
import com.woowa.accountbook.ui.history.HistoryViewModel
import com.woowa.accountbook.ui.iconpack.IconPack
import com.woowa.accountbook.ui.iconpack.LeftArrow
import com.woowa.accountbook.ui.iconpack.RightArrow
import com.woowa.accountbook.ui.iconpack.Trash
import com.woowa.accountbook.ui.theme.*

@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel = hiltViewModel(),
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    val inComeIsChecked = rememberSaveable { mutableStateOf(true) }
    val expenseIsChecked = rememberSaveable { mutableStateOf(false) }
    val editMode = remember { mutableStateOf(false) }
    val yearAndMonth = calendarViewModel.yearAndMonth.collectAsState().value
    val (year, month) = calendarViewModel.yearMonthPair.value
    historyViewModel.initHistory(month)
    onClickFilterButton(
        inComeIsChecked,
        expenseIsChecked,
        onIncomeClicked = { historyViewModel.getIncomeHistory() },
        onExpenseClicked = { historyViewModel.getExpenseHistory() },
        onBothClicked = { historyViewModel.getHistory() },
        onEmptyClicked = { historyViewModel.getEmptyHistory() }
    )

    Scaffold(
        backgroundColor = OffWhite,
        topBar = {
            if (!editMode.value) {
                AccountBookAppBar(
                    title = yearAndMonth,
                    navigationIcon = IconPack.LeftArrow,
                    onNavigationClicked = {
                        calendarViewModel.previousYearAndMonth()
                        inComeIsChecked.value = true
                        expenseIsChecked.value = false
                    },
                    actionIcon = IconPack.RightArrow,
                    onActionClicked = {
                        calendarViewModel.nextYearAndMonth()
                        inComeIsChecked.value = true
                        expenseIsChecked.value = false
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
                                        inComeIsChecked.value = true
                                        expenseIsChecked.value = false
                                    }
                                )
                            }
                        )
                    }
                )
            } else {
                val histories = historyViewModel.history.collectAsState().value
                AccountBookAppBar(
                    title = "${histories.count { it.isChecked }}개 선택",
                    navigationIcon = IconPack.LeftArrow,
                    onNavigationClicked = {
                        editMode.value = !editMode.value
                        historyViewModel.resetCheckedHistory()
                    },
                    actionIcon = IconPack.Trash,
                    actionIconColor = Red,
                    onActionClicked = {}
                )
            }
        }
    ) {
        val histories = historyViewModel.history.collectAsState().value
        val totalViewModel = historyViewModel.totalHistory.collectAsState().value

        if (inComeIsChecked.value && !expenseIsChecked.value) historyViewModel.getIncomeHistory()
        else if (!inComeIsChecked.value && expenseIsChecked.value) historyViewModel.getExpenseHistory()

        val groupHistory = histories.groupBy { it.day }
        val monthTotalIncome =
            totalViewModel.filter { it.category?.isIncome == 1 }.sumOf { it.money }
        val monthTotalExpense =
            totalViewModel.filter { it.category?.isIncome == 0 }.sumOf { it.money }

        Column(modifier = Modifier.fillMaxSize()) {
            FilterCheckBoxButton(
                totalIncome = monthTotalIncome,
                totalExpense = monthTotalExpense,
                inComeIsChecked = inComeIsChecked,
                expenseIsChecked = expenseIsChecked,
                enabled = !editMode.value,
                onIncomeCheckBoxClicked = { inComeIsChecked.value = it },
                onExpenseCheckBoxClicked = { expenseIsChecked.value = it },
                onIncomeButtonClicked = { inComeIsChecked.value = !inComeIsChecked.value },
                onExpenseButtonClicked = { expenseIsChecked.value = !expenseIsChecked.value },
                onIncomeClicked = { historyViewModel.getIncomeHistory() },
                onExpenseClicked = { historyViewModel.getExpenseHistory() },
                onBothClicked = { historyViewModel.getHistory() },
                onEmptyClicked = { historyViewModel.getEmptyHistory() }
            )
            if (groupHistory.isEmpty() || (!inComeIsChecked.value && !expenseIsChecked.value)) {
                Text(
                    modifier = Modifier
                        .fillMaxSize(),
                    textAlign = TextAlign.Center,
                    style = Typography.subtitle1,
                    color = LightPurple,
                    text = "내역이 없습니다."
                )
            } else {
                HistoryLazyColumn(
                    groupHistory,
                    editMode.value,
                    onLongClicked = { mode, id ->
                        editMode.value = !mode
                        historyViewModel.setCheckedItem(true, id)
                        if (!editMode.value) historyViewModel.resetCheckedHistory()
                    },
                    onCheckedItem = { isChecked, id ->
                        historyViewModel.setCheckedItem(
                            isChecked,
                            id
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun HistoryLazyColumn(
    groupHistory: Map<Int, List<History>>,
    editMode: Boolean,
    onLongClicked: (Boolean, Int) -> Unit,
    onCheckedItem: (Boolean, Int) -> Unit
) {
    LazyColumn {
        for (history in groupHistory) {
            val income =
                history.value.filter { it.category?.isIncome == 1 }.sumOf { it.money }
            val expense =
                history.value.filter { it.category?.isIncome == 0 }.sumOf { it.money }
            item {
                HistoryItemTitle(
                    textColor = LightPurple,
                    title = "${history.value[0].month}월 ${history.key}일",
                    income = rawToMoneyFormat(income, 1),
                    expense = rawToMoneyFormat(expense, 0)
                )
            }
            itemsIndexed(history.value) { _: Int, item: History ->
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Purple40)
                HistoryItem(
                    item,
                    editMode,
                    onLongClicked = { editMode, id -> onLongClicked(editMode, id) },
                    onCheckedItem = { isChecked, id -> onCheckedItem(isChecked, id) }
                )
            }
            item {
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(LightPurple)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryScreenPreview() {
    HistoryScreen()
}