package com.woowa.accountbook.ui.history

import androidx.lifecycle.ViewModel
import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val totalHistory = MutableStateFlow<List<History>>(emptyList())
    private val _history = MutableStateFlow<List<History>>(emptyList())
    val history: StateFlow<List<History>> get() = _history

    fun getHistory(month: Int) {
        val result = repository.getHistory(month).getOrThrow()
        totalHistory.value = result
        _history.value = result
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