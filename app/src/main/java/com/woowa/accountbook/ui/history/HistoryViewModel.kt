package com.woowa.accountbook.ui.history

import androidx.lifecycle.ViewModel
import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.domain.repository.history.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val historyRepository: HistoryRepository) :
    ViewModel() {

    val totalHistory = MutableStateFlow<List<History>>(emptyList())
    private val _history = MutableStateFlow<List<History>>(emptyList())
    val history: StateFlow<List<History>> get() = _history

    fun initHistory(month: Int) {
        val result = historyRepository.getHistoriesByMonth(month).getOrThrow()
        totalHistory.value = result
    }

    fun getHistory() {
        _history.value = totalHistory.value
    }

    fun getIncomeHistory() {
        val incomeHistory = totalHistory.value.filter { it.category.isIncome == 1 }
        _history.value = incomeHistory
    }

    fun getExpenseHistory() {
        val expenseHistory = totalHistory.value.filter { it.category.isIncome == 0 }
        _history.value = expenseHistory
    }

    fun getEmptyHistory() {
        _history.value = emptyList()
    }

    fun getHistoryMonthAndType(month: Int, type: Boolean) {
        val result = historyRepository.getHistoriesMonthAndType(month, type).getOrThrow()
        _history.value = result
    }

    fun resetCheckedHistory() {
        _history.value = _history.value.map {
            it.copy(isChecked = false)
        }
    }

    fun setCheckedItem(isChecked: Boolean, id: Int) {
        _history.value = _history.value.map {
            if (it.id == id) it.copy(isChecked = isChecked) else it
        }
    }
}