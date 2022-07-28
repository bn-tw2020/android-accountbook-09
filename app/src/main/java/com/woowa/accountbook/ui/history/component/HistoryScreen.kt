package com.woowa.accountbook.ui.history.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.ui.component.AccountBookAppBar
import com.woowa.accountbook.ui.component.LabelText
import com.woowa.accountbook.ui.component.LeftCornerCheckButton
import com.woowa.accountbook.ui.component.RightCornerCheckButton
import com.woowa.accountbook.ui.history.HistoryViewModel
import com.woowa.accountbook.ui.iconpack.IconPack
import com.woowa.accountbook.ui.iconpack.LeftArrow
import com.woowa.accountbook.ui.iconpack.RightArrow
import com.woowa.accountbook.ui.theme.*
import com.woowa.accountbook.ui.util.rawToMoneyFormat

@Composable
fun HistoryScreen(historyViewModel: HistoryViewModel = hiltViewModel()) {
    Scaffold(
        backgroundColor = OffWhite,
        topBar = {
            AccountBookAppBar(
                title = "2022년 7월",
                navigationIcon = IconPack.LeftArrow,
                onNavigationClicked = {},
                actionIcon = IconPack.RightArrow,
                onActionClicked = {}
            )
        }
    ) {
        val histories = historyViewModel.history.collectAsState().value
        val groupHistory = histories.groupBy { it.day }
        val monthTotalIncome = histories.filter { it.category.isIncome == 1 }.sumOf { it.money }
        val monthTotalExpense = histories.filter { it.category.isIncome == 0 }.sumOf { it.money }
        val inComeIsChecked = remember { mutableStateOf(true) }
        val expenseIsChecked = remember { mutableStateOf(true) }

        Column(modifier = Modifier.fillMaxSize()) {
            HistoryFilterButton(
                totalIncome = monthTotalIncome,
                totalExpense = monthTotalExpense,
                inComeIsChecked = inComeIsChecked,
                expenseIsChecked = expenseIsChecked,
                onIncomeCheckBoxClicked = { inComeIsChecked.value = it },
                onExpenseCheckBoxClicked = { expenseIsChecked.value = it },
                onIncomeButtonClicked = { inComeIsChecked.value = !inComeIsChecked.value },
                onExpenseButtonClicked = { expenseIsChecked.value = !expenseIsChecked.value },
                onIncomeClicked = { historyViewModel.getIncomeHistory() },
                onExpenseClicked = { historyViewModel.getExpenseHistory() },
                onBothClicked = { historyViewModel.getHistory(7) },
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
                HistoryLazyColumn(groupHistory)
            }
        }
    }
}

@Composable
private fun HistoryLazyColumn(groupHistory: Map<Int, List<History>>) {
    LazyColumn {
        for (history in groupHistory) {
            val income =
                history.value.filter { it.category.isIncome == 1 }.sumOf { it.money }
            val expense =
                history.value.filter { it.category.isIncome == 0 }.sumOf { it.money }
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
                HistoryItem(item)
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

@Composable
private fun HistoryFilterButton(
    totalIncome: Int,
    totalExpense: Int,
    inComeIsChecked: MutableState<Boolean>,
    expenseIsChecked: MutableState<Boolean>,
    onIncomeCheckBoxClicked: (Boolean) -> Unit,
    onExpenseCheckBoxClicked: (Boolean) -> Unit,
    onIncomeButtonClicked: () -> Unit,
    onExpenseButtonClicked: () -> Unit,
    onIncomeClicked: () -> Unit,
    onExpenseClicked: () -> Unit,
    onBothClicked: () -> Unit,
    onEmptyClicked: () -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        LeftCornerCheckButton(
            checkBox = true,
            checked = inComeIsChecked.value,
            onClicked = {
                onIncomeButtonClicked()
                onClickFilterButton(
                    inComeIsChecked,
                    expenseIsChecked,
                    onBothClicked,
                    onIncomeClicked,
                    onExpenseClicked,
                    onEmptyClicked
                )
            },
            onCheckedChange = {
                onIncomeCheckBoxClicked(it)
                onClickFilterButton(
                    inComeIsChecked,
                    expenseIsChecked,
                    onBothClicked,
                    onIncomeClicked,
                    onExpenseClicked,
                    onEmptyClicked
                )
            },
            checkedColor = White,
            uncheckedColor = White,
            checkmarkColor = Purple,
            labelText = "수입",
            labelPriceText = rawToMoneyFormat(totalIncome, 1)
        )

        RightCornerCheckButton(
            checkBox = true,
            checked = expenseIsChecked.value,
            onClicked = {
                onExpenseButtonClicked()
                onClickFilterButton(
                    inComeIsChecked,
                    expenseIsChecked,
                    onBothClicked,
                    onIncomeClicked,
                    onExpenseClicked,
                    onEmptyClicked
                )
            },
            onCheckedChange = {
                onExpenseCheckBoxClicked(it)
                onClickFilterButton(
                    inComeIsChecked,
                    expenseIsChecked,
                    onBothClicked,
                    onIncomeClicked,
                    onExpenseClicked,
                    onEmptyClicked
                )
            },
            checkedColor = White,
            uncheckedColor = White,
            checkmarkColor = Purple,
            labelText = "지출",
            labelPriceText = rawToMoneyFormat(totalExpense, 0)
        )
    }
}

@Composable
fun HistoryItemTitle(
    textColor: Color,
    title: String,
    income: String,
    expense: String
) {
    Spacer(modifier = Modifier.height(24.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = textColor
        )

        Row {
            Text(
                text = "수입",
                style = MaterialTheme.typography.caption,
                color = textColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = income,
                style = MaterialTheme.typography.caption,
                color = textColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "지출",
                style = MaterialTheme.typography.caption,
                color = textColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = expense,
                style = MaterialTheme.typography.caption,
                color = textColor
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun HistoryItem(history: History) {
    Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LabelText(
                text = history.category.name,
                textStyle = MaterialTheme.typography.caption,
                color = Color(android.graphics.Color.parseColor(history.category.color))
            )
            Text(
                text = history.payment.name,
                style = MaterialTheme.typography.caption,
                color = Purple
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = history.content,
                style = MaterialTheme.typography.subtitle2,
                color = Purple
            )
            Text(
                text = "${rawToMoneyFormat(history.money, history.category.isIncome)}원",
                style = MaterialTheme.typography.subtitle2,
                color = Red
            )
        }
    }
}

private fun onClickFilterButton(
    inComeIsChecked: MutableState<Boolean>,
    expenseIsChecked: MutableState<Boolean>,
    onBothClicked: () -> Unit,
    onIncomeClicked: () -> Unit,
    onExpenseClicked: () -> Unit,
    onEmptyClicked: () -> Unit
) {
    if (inComeIsChecked.value && expenseIsChecked.value) onBothClicked()
    else if (inComeIsChecked.value) onIncomeClicked()
    else if (expenseIsChecked.value) onExpenseClicked()
    else onEmptyClicked()
}

@Preview(showBackground = true)
@Composable
private fun HistoryScreenPreview() {
    HistoryScreen()
}