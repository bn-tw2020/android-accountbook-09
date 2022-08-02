package com.woowa.accountbook.ui.settings

import androidx.lifecycle.ViewModel
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

    init {
        getPayments()
        getExpenseCategories()
        getIncomeCategories()
    }

    fun getPayments() {
        val paymentList = paymentRepository.getPayments().getOrThrow()
        _payments.value = paymentList
    }

    fun getIncomeCategories() {
        val categoryList = categoryRepository.getCategoriesByType("1").getOrThrow()
        _incomeCategories.value = categoryList
    }

    fun getExpenseCategories() {
        val categoryList = categoryRepository.getCategoriesByType("0").getOrThrow()
        _expenseCategories.value = categoryList
    }

    fun setCheckedItem(isChecked: Boolean, id: Int?, type: String) {
        when(type) {
            "payment" -> {
                _payments.value = _payments.value.map {
                    if(it.id == id) it.copy(isChecked = isChecked) else it
                }
            }
            "expense" -> {
                _expenseCategories.value = _expenseCategories.value.map {
                    if(it.id == id) it.copy(isChecked = isChecked) else it
                }
            }
            "income" -> {
                _incomeCategories.value = _incomeCategories.value.map {
                    if(it.id == id) it.copy(isChecked = isChecked) else it
                }
            }
        }
    }

    fun resetCheckedItem(type: String) {
        when(type) {
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
        when(type) {
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
}