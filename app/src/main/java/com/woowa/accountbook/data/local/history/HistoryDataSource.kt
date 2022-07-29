package com.woowa.accountbook.data.local.history

import com.woowa.accountbook.data.entitiy.History

interface HistoryDataSource {

    fun findByAll(): List<History>
    fun findByMonth(month: String): List<History>
    fun findByMonthAndType(month: String, type: Boolean): List<History>
    fun deleteById(list: List<Int>)
    fun save(
        money: Int,
        categoryId: Int,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    )
}