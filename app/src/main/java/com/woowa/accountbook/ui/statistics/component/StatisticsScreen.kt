package com.woowa.accountbook.ui.statistics.component

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.woowa.accountbook.common.rawToMoneyFormat
import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.ui.calendar.CalendarViewModel
import com.woowa.accountbook.ui.component.*
import com.woowa.accountbook.ui.history.HistoryViewModel
import com.woowa.accountbook.ui.iconpack.IconPack
import com.woowa.accountbook.ui.iconpack.LeftArrow
import com.woowa.accountbook.ui.iconpack.RightArrow
import com.woowa.accountbook.ui.theme.*
import kotlin.math.round

@Composable
fun StatisticsScreen(
    historyViewModel: HistoryViewModel = hiltViewModel(),
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    val yearAndMonth = calendarViewModel.yearAndMonth.collectAsState().value
    val (year, month) = calendarViewModel.yearMonthPair.value
    historyViewModel.getHistoryMonthAndType(month, false)
    val histories = historyViewModel.history.collectAsState().value
    val groupBy = histories.groupBy { Pair(it.category.name, it.category.color) }
    val totalExpense = histories.sumOf { it.money }

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
        if (histories.isEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxSize(),
                textAlign = TextAlign.Center,
                style = Typography.subtitle1,
                color = LightPurple,
                text = "내역이 없습니다."
            )
        } else {
            val expenseCategoryList = groupBy.map {
                val categorySum = it.value.fold(0) { sum, history -> sum + history.money }
                Pair(it.key, categorySum)
            }.sortedByDescending { it.second }
            ExpenseCategory(totalExpense, groupBy, expenseCategoryList)
        }
    }
}

@Composable
private fun ExpenseCategory(
    totalExpense: Int,
    groupBy: Map<Pair<String, String>, List<History>>,
    expenseCategoryList: List<Pair<Pair<String, String>, Int>>
) {
    Column {
        BothSideText(
            leftText = {
                Text(
                    text = "이번 달 총 지출 금액",
                    style = MaterialTheme.typography.subtitle2,
                    color = Purple,
                )
            },
            rightText = {
                Text(
                    text = rawToMoneyFormat(totalExpense, 1),
                    style = MaterialTheme.typography.subtitle2,
                    color = Red
                )
            }
        )
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(LightPurple)
        )
        Spacer(modifier = Modifier.height(23.dp))
        ExpenseGraph(groupBy, totalExpense)
        GraphLegend(expenseCategoryList, totalExpense)
    }
}

@Composable
private fun GraphLegend(
    expenseCategoryList: List<Pair<Pair<String, String>, Int>>,
    totalExpense: Int
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
    ) {
        itemsIndexed(expenseCategoryList) { index: Int, category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 9.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    LabelText(
                        text = category.first.first,
                        textStyle = MaterialTheme.typography.caption,
                        color = Color(android.graphics.Color.parseColor(category.first.second))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = rawToMoneyFormat(category.second, 1),
                        style = MaterialTheme.typography.body2,
                        color = Purple
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "${round(100 * category.second / totalExpense.toFloat())}%",
                    style = MaterialTheme.typography.subtitle2,
                    color = Purple
                )
            }
            if (expenseCategoryList.size != (index + 1)) Divider(color = Purple40)
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

@Composable
private fun ExpenseGraph(
    groupBy: Map<Pair<String, String>, List<History>>,
    totalExpense: Int,
) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
        factory = { context ->

            val pieChart = PieChart(context)
            pieChart.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            pieChart.description.isEnabled = false
            pieChart.legend.isEnabled = false
            pieChart.isRotationEnabled = false
            pieChart.transparentCircleRadius = 0f
            pieChart.setTouchEnabled(false)
            pieChart.setHoleColor(0)


            val noOfEmp = mutableListOf<PieEntry>()
            groupBy.forEach { (key, value) ->
                val fold = value.fold(0) { sum, history -> sum + history.money }
                noOfEmp.add(
                    PieEntry(
                        fold.toFloat(),
                        round(100 * fold / totalExpense.toFloat())
                    )
                )
            }
            noOfEmp.sortByDescending { it.value }

            val dataSet = PieDataSet(noOfEmp, "Expense")
            dataSet.setDrawValues(false)
            val pieData = PieData(dataSet)
            val categoryColorList = groupBy.map { (key, value) ->
                android.graphics.Color.parseColor(key.second)
            }
            dataSet.colors = categoryColorList
            pieChart.data = pieData
            pieChart.animateY(1000, Easing.EaseInBack)

            pieChart
        },
        update = { pieChart ->
            val noOfEmp = mutableListOf<PieEntry>()
            groupBy.forEach { (key, value) ->
                val fold = value.fold(0) { sum, history -> sum + history.money }
                noOfEmp.add(
                    PieEntry(
                        fold.toFloat(),
                        round(100 * fold / totalExpense.toFloat())
                    )
                )
            }
            noOfEmp.sortByDescending { it.value }

            val dataSet = PieDataSet(noOfEmp, "Expense")
            dataSet.setDrawValues(false)
            val pieData = PieData(dataSet)
            val categoryColorList = groupBy.map { (key, value) ->
                android.graphics.Color.parseColor(key.second)
            }
            dataSet.colors = categoryColorList
            pieChart.data = pieData
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun StatisticsScreenPreview() {
    StatisticsScreen()
}