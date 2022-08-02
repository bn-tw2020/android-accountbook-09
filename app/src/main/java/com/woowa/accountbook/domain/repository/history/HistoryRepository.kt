package com.woowa.accountbook.domain.repository.history

import com.woowa.accountbook.data.entitiy.History

interface HistoryRepository {
    fun getHistory(id: Int): Result<History?>
    fun getHistories(): Result<List<History>>
    fun getHistoriesByMonth(month: Int): Result<List<History>>
    fun getHistoriesMonthAndType(month: Int, type: Boolean): Result<List<History>>
    fun removeHistories(list: List<Int>)
    fun updateHistory(
        id: Int,
        money: Int,
        categoryId: Int?,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    )

    fun saveHistory(
        money: Int,
        categoryId: Int?,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    )
}