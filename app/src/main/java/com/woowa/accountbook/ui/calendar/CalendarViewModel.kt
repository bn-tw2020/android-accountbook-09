package com.woowa.accountbook.ui.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import com.woowa.accountbook.common.NOW_DAY
import com.woowa.accountbook.common.NOW_MONTH
import com.woowa.accountbook.common.NOW_YEAR
import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.domain.entity.DateItem
import com.woowa.accountbook.ui.theme.LightPurple
import com.woowa.accountbook.ui.theme.Purple
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val calendar: CustomCalendar) : ViewModel() {

    private val _yearAndMonth = MutableStateFlow(calendar.currentYearAndMonth)
    val yearAndMonth: StateFlow<String> get() = _yearAndMonth

    private val _yearMonthPair = MutableStateFlow(calendar.yearMonthPair)
    val yearMonthPair: StateFlow<Pair<Int, Int>> get() = _yearMonthPair

    private val _dateList = MutableStateFlow<List<DateItem>>(emptyList())
    val dateList: StateFlow<List<DateItem>> get() = _dateList

    private val _selectedDate = MutableStateFlow<DateItem?>(null)
    val selectedDate: StateFlow<DateItem?> get() = _selectedDate

    fun selectedDate(dateItem: DateItem) {
        _selectedDate.value = dateItem
        Log.d("test", "selectedDate: ${selectedDate.value?.date}")
    }

    fun setYearAndMonth(year: Int, month: Int) {
        _yearAndMonth.value = calendar.setYearAndMonth(year, month)
        _yearMonthPair.value = calendar.yearMonthPair
    }

    fun previousYearAndMonth() {
        _yearAndMonth.value = calendar.previousYearAndMonth()
        _yearMonthPair.value = calendar.yearMonthPair
    }

    fun nextYearAndMonth() {
        _yearAndMonth.value = calendar.nextYearAndMonth()
        _yearMonthPair.value = calendar.yearMonthPair
    }

    fun getAllDate(histories: List<History>) {
        val dateItems = mutableListOf<DateItem>()
        calendar.dateList.forEachIndexed { index, pair ->
            if (pair.first < 0) {
                dateItems.add(DateItem(null, null, 0, 0, pair.second, LightPurple))
            } else if (pair.first > 0) {
                dateItems.add(DateItem(null, null, 0, 0, pair.second, LightPurple))
            } else {
                val groupHistory = histories.groupBy { it.day }
                val dayList = groupHistory[pair.second]

                if (dayList == null) {
                    dateItems.add(
                        DateItem(
                            expense = null,
                            income = null,
                            year = _yearMonthPair.value.first,
                            month = _yearMonthPair.value.second,
                            date = pair.second,
                            color = Purple
                        )
                    )
                } else {
                    val income = dayList
                        .filter { it.category?.isIncome == 1 }
                        .sumOf { it.money }
                    val expense = dayList
                        .filter { it.category?.isIncome == 0 }
                        .sumOf { it.money }

                    dateItems.add(
                        DateItem(
                            expense = expense,
                            income = income,
                            year = _yearMonthPair.value.first,
                            month = _yearMonthPair.value.second,
                            date = pair.second,
                            color = Purple
                        )
                    )
                }
                _dateList.value = dateItems
            }
        }
    }

    fun isToday(dateItem: DateItem): Boolean {
        return dateItem.year == NOW_YEAR && dateItem.month == NOW_MONTH && dateItem.date == NOW_DAY
    }

    fun getDayOfWeek(year: Int, month: Int, date: Int): String {
        return calendar.getDayOfWeek(year, month, date)
    }

    fun getMaxDate(year: Int, month: Int, date: Int): Int {
        return calendar.getMaxDate(year, month, date)
    }
}