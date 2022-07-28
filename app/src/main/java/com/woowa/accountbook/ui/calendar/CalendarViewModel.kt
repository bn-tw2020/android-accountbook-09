package com.woowa.accountbook.ui.calendar

import androidx.lifecycle.ViewModel
import com.woowa.accountbook.common.rawToYearAndMonth
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
}