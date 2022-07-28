package com.woowa.accountbook.ui.calendar

import com.woowa.accountbook.common.NOW_MONTH
import com.woowa.accountbook.common.NOW_YEAR
import com.woowa.accountbook.common.rawToYearAndMonth
import java.util.*

class CustomCalendar {

    private val calendar = Calendar.getInstance()

    val currentYearAndMonth = "${NOW_YEAR}년 ${NOW_MONTH}월"
    var yearMonthPair = Pair(NOW_YEAR, NOW_MONTH)

    fun setYearAndMonth(year: Int, month: Int): String {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        yearMonthPair = Pair(year, month)
        return rawToYearAndMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)
    }

    fun previousYearAndMonth(): String {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        if (month == 0) {
            calendar.set(Calendar.YEAR, year - 1)
            calendar.set(Calendar.MONTH, 11)
        } else {
            calendar.set(Calendar.MONTH, month - 1)
        }
        yearMonthPair = Pair(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)
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
        return rawToYearAndMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)
    }


}