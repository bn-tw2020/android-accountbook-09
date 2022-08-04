package com.woowa.accountbook.ui.statistics.component

import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.woowa.accountbook.data.entitiy.History
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
    historyViewModel.getExpenseHistories(year)
    val histories = historyViewModel.history.collectAsState().value
    val expenseHistories = historyViewModel.yearHistory.collectAsState().value
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
            ExpenseGraph(expenseHistories, id)
            HistoryLazyColumn(groupHistory)
        }
    }
}

@Composable
private fun ExpenseGraph(expenseHistories: List<History>, id: Int?) {
    val name = expenseHistories.find { it.category?.id == id }?.category?.name ?: ""
    val filter = expenseHistories.filter { it.category?.name == name }
    val groupBy = filter.groupBy { it.month }
    val graphData = groupBy.map { (month, value) ->
        val sumOf = value.sumOf { it.money }
        month to sumOf
    }
    val minPrice = graphData.minOf { it.second }
    val maxPrice = graphData.maxOf { it.second }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        factory = { context ->

            val lineChart = LineChart(context)
            lineChart.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            lineChart.description.isEnabled = false
            lineChart.legend.isEnabled = false
            lineChart.setTouchEnabled(false)
            lineChart.isDragXEnabled = true
            val xAxis = lineChart.xAxis
            with(xAxis) {
                setDrawAxisLine(false)
                setDrawGridLines(false)
                position = XAxis.XAxisPosition.BOTTOM
                setLabelCount(12, true)
                granularity = 5f
                spaceMin = 0.1f
                spaceMax = 0.1f
                axisMinimum = 1f
                axisMaximum = 12f
            }

            val yAxisLeft = lineChart.axisLeft
            with(yAxisLeft) {
                setDrawLabels(false)
                setDrawAxisLine(false)
                setDrawGridLines(false)
                granularity = (graphData.sumOf { it.second } / graphData.size).toFloat()
                axisMinimum = minPrice.toFloat()
                axisMaximum = maxPrice.toFloat()
            }

            val yAxisRight: YAxis = lineChart.axisRight
            with(yAxisRight) {
                setDrawLabels(false)
                setDrawAxisLine(false)
                setDrawGridLines(false)
            }

            val noOfEmp = mutableListOf<Entry>()
            graphData.forEach {
                noOfEmp.add(
                    Entry(it.first.toFloat(), it.second.toFloat())
                )
            }

            val dataSet = LineDataSet(noOfEmp, "Expense")
            val lineData = LineData(dataSet)

            dataSet.lineWidth = 3f
            dataSet.valueTextSize = 10f
            dataSet.setDrawValues(false)
            dataSet.setDrawCircleHole(false)
            dataSet.setDrawCircles(false)
            dataSet.setDrawHorizontalHighlightIndicator(false)
            dataSet.setDrawHighlightIndicators(false)
            dataSet.color = Color.parseColor("#524D90")

            lineChart.data = lineData
            lineChart.animateXY(500, 500)
            lineChart
        }
    )
}