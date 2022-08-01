package com.woowa.accountbook.ui.calendar

import com.woowa.accountbook.common.NOW_MONTH
import com.woowa.accountbook.common.NOW_YEAR
import com.woowa.accountbook.common.rawToYearAndMonth
import java.util.*

class CustomCalendar {

    private val calendar = Calendar.getInstance()

    val currentYearAndMonth = "${NOW_YEAR}년 ${NOW_MONTH}월"
    var yearMonthPair = Pair(NOW_YEAR, NOW_MONTH)

    var currentMaxDate = 0
    private var previousMonthOffset = 0
    var nextMonthOffset = 0

    private val _dateList = mutableListOf<Pair<Int, Int>>()
    val dateList: List<Pair<Int, Int>> get() = _dateList

    init {
        getMonthDate(calendar)
    }

    fun setYearAndMonth(year: Int, month: Int): String {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        yearMonthPair = Pair(year, month)
        getMonthDate(calendar.clone() as Calendar)
        return rawToYearAndMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)
    }

    fun previousYearAndMonth(): String {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        calendar.set(Calendar.DATE, 1)

        if (month == 0) {
            calendar.set(Calendar.YEAR, year - 1)
            calendar.set(Calendar.MONTH, 11)
        } else {
            calendar.set(Calendar.MONTH, month - 1)
        }
        yearMonthPair = Pair(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)
        getMonthDate(calendar.clone() as Calendar)
        return rawToYearAndMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)
    }

    fun nextYearAndMonth(): String {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        if (month == 12) {
            calendar.set(Calendar.YEAR, year + 1)
            calendar.set(Calendar.MONTH, 0)
        } else {
            calendar.set(Calendar.MONTH, month + 1)
        }
        yearMonthPair = Pair(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)
        getMonthDate(calendar.clone() as Calendar)
        return rawToYearAndMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)
    }

    private fun getMonthDate(calendar: Calendar) {
        _dateList.clear()

        calendar.set(Calendar.DATE, 1)
        currentMaxDate = calendar.getActualMaximum(Calendar.DATE)
        previousMonthOffset = calendar.get(Calendar.DAY_OF_WEEK) - 1
        getPreviousMonthOffsetDate(calendar.clone() as Calendar)

        getCurrentMonthDate(calendar)

        calendar.set(Calendar.DATE, currentMaxDate)
        nextMonthOffset = calendar.get(Calendar.DAY_OF_WEEK)
        getNextMonthOffsetDate()
    }

    private fun getNextMonthOffsetDate() {
        var nextDate = 0
        repeat(Calendar.DAY_OF_WEEK - nextMonthOffset) {
            _dateList.add(1 to ++nextDate)
        }
    }

    private fun getPreviousMonthOffsetDate(calendar: Calendar) {
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1)
        var previousMaxDate = calendar.getActualMaximum(Calendar.DATE) - previousMonthOffset
        repeat(previousMonthOffset) {
            _dateList.add(-1 to ++previousMaxDate)
        }
    }

    private fun getCurrentMonthDate(calendar: Calendar) {
        repeat(calendar.getActualMaximum(Calendar.DATE)) {
            _dateList.add(0 to it + 1)
        }
    }
}