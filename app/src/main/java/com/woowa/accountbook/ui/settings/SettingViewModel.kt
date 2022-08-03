package com.woowa.accountbook.ui.settings

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.woowa.accountbook.common.toHex
import com.woowa.accountbook.data.entitiy.Category
import com.woowa.accountbook.data.entitiy.Payment
import com.woowa.accountbook.domain.repository.category.CategoryRepository
import com.woowa.accountbook.domain.repository.payment.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _payments = MutableStateFlow<List<Payment>>(emptyList())
    val payments: StateFlow<List<Payment>> get() = _payments

    private val _incomeCategories = MutableStateFlow<List<Category>>(emptyList())
    val incomeCategories: StateFlow<List<Category>> get() = _incomeCategories

    private val _expenseCategories = MutableStateFlow<List<Category>>(emptyList())
    val expenseCategories: StateFlow<List<Category>> get() = _expenseCategories

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage: StateFlow<String> get() = _errorMessage

    init {
        getPayments()
        getExpenseCategories()
        getIncomeCategories()
    }

    private fun getPayments() {
        val paymentList = paymentRepository.getPayments().getOrThrow()
        _payments.value = paymentList
    }

    private fun getIncomeCategories() {
        val categoryList = categoryRepository.getCategoriesByType("1").getOrThrow()
        _incomeCategories.value = categoryList
    }

    private fun getExpenseCategories() {
        val categoryList = categoryRepository.getCategoriesByType("0").getOrThrow()
        _expenseCategories.value = categoryList
    }

    fun savePayment(name: String) {
        paymentRepository.savePayment(name)
        getPayments()
    }

    fun saveCategory(name: String, isIncome: Boolean, color: Color) {
        categoryRepository.saveCategory(name, color.toHex(), isIncome)
        getIncomeCategories()
        getExpenseCategories()
    }

    fun updatePayment(id: Int, name: String) {
        paymentRepository.updatePayment(id, name)
        getPayments()
    }

    fun updateCategory(id: Int, name: String, color: Color, isIncome: Boolean) {
        categoryRepository.updateCategory(id, name, color.toHex(), isIncome)
        getIncomeCategories()
        getExpenseCategories()
    }

    fun removePayments() {
        val selectedPayment = _payments.value
            .filter { it.isChecked }
            .map { it.id }
        paymentRepository.removePayments(selectedPayment)
            .onSuccess { result ->
                if (result) _payments.value = _payments.value.filter { !it.isChecked }
                else _errorMessage.value = "내역에서 사용되고 있는 결제수단이 존재합니다."

            }
            .onFailure {
                _errorMessage.value = "삭제 중 에러가 발생했습니다."
            }
    }

    fun removeCategories(type: String) {
        when (type) {
            "expense" -> {
                val selectedCategory = _expenseCategories.value
                    .filter { it.isChecked }
                    .map { it.id!! }
                categoryRepository.removeCategories(selectedCategory)
                    .onSuccess { result ->
                        if (result) _expenseCategories.value =
                            _expenseCategories.value.filter { !it.isChecked }
                        else _errorMessage.value = "내역에서 사용되고 있는 카테고리가 존재합니다."
                    }
                    .onFailure {
                        _errorMessage.value = "삭제 중 에러가 발생했습니다."
                    }
            }
            "income" -> {
                val selectedCategory = _incomeCategories.value
                    .filter { it.isChecked }
                    .map { it.id!! }
                categoryRepository.removeCategories(selectedCategory)
                    .onSuccess { result ->
                        if (result) _incomeCategories.value =
                            _incomeCategories.value.filter { !it.isChecked }
                        else _errorMessage.value = "내역에서 사용되고 있는 카테고리가 존재합니다."
                    }
                    .onFailure {
                        _errorMessage.value = "삭제 중 에러가 발생했습니다."
                    }
            }
        }
    }

    fun setCheckedItem(isChecked: Boolean, id: Int?, type: String) {
        when (type) {
            "payment" -> {
                _payments.value = _payments.value.map {
                    if (Payment.isDefault(it.name)) it
                    else if (it.id == id) it.copy(isChecked = isChecked) else it
                }
            }
            "expense" -> {
                _expenseCategories.value = _expenseCategories.value.map {
                    if (Category.isDefault(it.name)) it
                    else if (it.id == id) it.copy(isChecked = isChecked) else it
                }
            }
            "income" -> {
                _incomeCategories.value = _incomeCategories.value.map {
                    if (Category.isDefault(it.name)) it
                    else if (it.id == id) it.copy(isChecked = isChecked) else it
                }
            }
        }
    }

    fun resetCheckedItem(type: String) {
        when (type) {
            "payment" -> {
                _payments.value = _payments.value.map {
                    it.copy(isChecked = false)
                }
            }
            "expense" -> {
                _expenseCategories.value = _expenseCategories.value.map {
                    it.copy(isChecked = false)
                }
            }
            "income" -> {
                _incomeCategories.value = _incomeCategories.value.map {
                    it.copy(isChecked = false)
                }
            }
        }
    }

    fun removeItem(type: String) {
        when (type) {
            "payment" -> {
                resetCheckedItem(type)
            }
            "expense" -> {
                resetCheckedItem(type)
            }
            "income" -> {
                resetCheckedItem(type)
            }
        }
    }

    fun initErrorMessage() {
        _errorMessage.value = ""
    }
}