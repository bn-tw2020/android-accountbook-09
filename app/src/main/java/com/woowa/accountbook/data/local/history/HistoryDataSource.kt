package com.woowa.accountbook.data.local.history

import com.woowa.accountbook.data.entitiy.History

interface HistoryDataSource {

    suspend fun findById(id: Int): History?
    fun existsByCategoryId(id: Int): Boolean
    fun existsByPaymentId(id: Int): Boolean
    suspend fun findByAll(year: Int): List<History>
    suspend fun findByMonth(month: String): List<History>
    suspend fun findByMonthAndType(month: String, type: Boolean): List<History>
    suspend fun deleteById(list: List<Int>)
    suspend fun update(
        id: Int,
        money: Int,
        categoryId: Int?,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    )

    suspend fun save(
        money: Int,
        categoryId: Int?,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    )
}