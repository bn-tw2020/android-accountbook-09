package com.woowa.accountbook.ui.history

import androidx.lifecycle.ViewModel
import com.woowa.accountbook.data.entitiy.Category
import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.data.entitiy.Payment
import com.woowa.accountbook.domain.repository.category.CategoryRepository
import com.woowa.accountbook.domain.repository.history.HistoryRepository
import com.woowa.accountbook.domain.repository.payment.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val paymentRepository: PaymentRepository,
    private val categoryRepository: CategoryRepository
) :
    ViewModel() {

    val totalHistory = MutableStateFlow<List<History>>(emptyList())
    private val _history = MutableStateFlow<List<History>>(emptyList())
    val history: StateFlow<List<History>> get() = _history

    private val _payments = MutableStateFlow<List<Payment>>(emptyList())
    val payments: StateFlow<List<Payment>> get() = _payments

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    init {
        savePayment("현대카드")
        savePayment("카카오뱅크 체크카드")
        saveCategory("교통", "#94D3CC", false)
        saveCategory("문화/여가", "#D092E2", false)
        saveCategory("미분류", "#817DCE", false)
        saveCategory("생활", "#4A6CC3", false)
        saveCategory("쇼핑/뷰티", "#4CB8B8", false)
        saveCategory("식비", "#4CA1DE", false)
        saveCategory("의료/건강", "#6ED5EB", false)
        saveCategory("월급", "#9BD182", true)
        saveCategory("용돈", "#EDCF65", true)
        saveCategory("기타", "#E29C4D", true)
        getPayments()
    }

    fun initHistory(month: Int) {
        val result = historyRepository.getHistoriesByMonth(month).getOrThrow()
        totalHistory.value = result
    }

    fun getHistory() {
        _history.value = totalHistory.value
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

    fun getHistoryMonthAndType(month: Int, type: Boolean) {
        val result = historyRepository.getHistoriesMonthAndType(month, type).getOrThrow()
        _history.value = result
    }

    fun saveHistory(
        money: Int,
        categoryId: Int?,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    ) {
        historyRepository.saveHistory(money, categoryId, content, year, month, day, paymentId)
    }

    private fun saveCategory(name: String, color: String, isIncome: Boolean) {
        categoryRepository.saveCategory(name, color, isIncome)
    }

    private fun savePayment(name: String) {
        paymentRepository.savePayment(name)
    }

    private fun getPayments() {
        val paymentList = paymentRepository.getPayments().getOrThrow()
        _payments.value = paymentList
    }

    fun getCategories(type: String) {
        val categoryList = categoryRepository.getCategoriesByType(type).getOrThrow()
        _categories.value = categoryList
    }

    fun getDefaultCategory(): Int? = categories.value.find { it.name == "미분류" }?.id

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