package com.woowa.accountbook.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.accountbook.data.entitiy.Category
import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.data.entitiy.Payment
import com.woowa.accountbook.domain.repository.category.CategoryRepository
import com.woowa.accountbook.domain.repository.history.HistoryRepository
import com.woowa.accountbook.domain.repository.payment.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val paymentRepository: PaymentRepository,
    private val categoryRepository: CategoryRepository
) :
    ViewModel() {

    private val _yearHistory = MutableStateFlow<List<History>>(emptyList())
    val yearHistory: StateFlow<List<History>> get() = _yearHistory

    val totalHistory = MutableStateFlow<List<History>>(emptyList())
    private val _history = MutableStateFlow<List<History>>(emptyList())
    val history: StateFlow<List<History>> get() = _history

    var currentHistory: History? = null

    private val _payments = MutableStateFlow<List<Payment>>(emptyList())
    val payments: StateFlow<List<Payment>> get() = _payments

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    init {
        getPayments()
    }

    fun initHistory(month: Int) = viewModelScope.launch {
        val result = historyRepository.getHistoriesByMonth(month).getOrThrow()
        totalHistory.value = result
    }

    fun getHistory() {
        _history.value = totalHistory.value
    }

    fun getExpenseHistories(year: Int) = viewModelScope.launch {
        _yearHistory.value = historyRepository.getExpenseHistories(year).getOrThrow()
    }

    fun getIncomeHistory() {
        val incomeHistory = totalHistory.value.filter { it.category?.isIncome == 1 }
        _history.value = incomeHistory
    }

    fun getExpenseHistory() {
        val expenseHistory = totalHistory.value.filter { it.category?.isIncome == 0 }
        _history.value = expenseHistory
    }

    fun getEmptyHistory() {
        _history.value = emptyList()
    }

    fun getHistoryMonthAndType(month: Int, type: Boolean) = viewModelScope.launch {
        val result = historyRepository.getHistoriesMonthAndType(month, type).getOrThrow()
        _history.value = result
    }

    fun removeHistory() = viewModelScope.launch {
        val selectedHistory = _history.value
            .filter { it.isChecked }
            .map { it.id }
        _history.value = _history.value.filter { !it.isChecked }
        historyRepository.removeHistories(selectedHistory)
    }

    fun saveHistory(
        money: Int,
        categoryId: Int?,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    ) = viewModelScope.launch {
        historyRepository.saveHistory(money, categoryId, content, year, month, day, paymentId)
    }

    fun updateHistory(
        id: Int,
        money: Int,
        categoryId: Int?,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    ) = viewModelScope.launch {
        historyRepository.updateHistory(id, money, categoryId, content, year, month, day, paymentId)
    }

    fun getPayments() = viewModelScope.launch {
        val paymentList = paymentRepository.getPayments().getOrThrow()
        _payments.value = paymentList
    }

    fun getCategories(type: String) = viewModelScope.launch {
        val categoryList = categoryRepository.getCategoriesByType(type).getOrThrow()
        _categories.value = categoryList
    }

    fun getDefaultCategory(): Int? = categories.value.find { it.name == "미분류" }?.id

    fun resetCheckedHistory() {
        totalHistory.value = totalHistory.value.map {
            it.copy(isChecked = false)
        }
    }

    fun setCheckedItem(isChecked: Boolean, id: Int) {
        totalHistory.value = totalHistory.value.map {
            if (it.id == id) it.copy(isChecked = isChecked) else it
        }
        _history.value = _history.value.map {
            if (it.id == id) it.copy(isChecked = isChecked) else it
        }
    }
}