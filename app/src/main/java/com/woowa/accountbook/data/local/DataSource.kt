package com.woowa.accountbook.data.local

import com.woowa.accountbook.data.entitiy.Category
import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.data.entitiy.Payment

interface DataSource {

    fun findByCategoryType(type: String): List<Category>
    fun findAllPayment(): List<Payment>
    fun findByHistoryMonth(month: String): List<History>
    fun deleteByHistoryId(list: List<Int>)
    fun deleteByCategoryId(list: List<Int>)
    fun deleteByPaymentId(list: List<Int>)
    fun insertCategory(name: String, color: String, isIncome: Boolean)
    fun insertPayment(name: String)
    fun insertHistory(
        money: Int,
        categoryId: Int,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    )
}