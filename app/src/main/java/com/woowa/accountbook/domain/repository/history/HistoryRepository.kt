package com.woowa.accountbook.domain.repository.history

import com.woowa.accountbook.data.entitiy.History

interface HistoryRepository {
    suspend fun getHistory(id: Int): Result<History?>
    suspend fun getExpenseHistories(year: Int): Result<List<History>>
    suspend fun getHistoriesByMonth(month: Int): Result<List<History>>
    suspend fun getHistoriesMonthAndType(month: Int, type: Boolean): Result<List<History>>
    suspend fun removeHistories(list: List<Int>)
    suspend fun updateHistory(
        id: Int,
        money: Int,
        categoryId: Int?,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    )

    suspend fun saveHistory(
        money: Int,
        categoryId: Int?,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    )
}