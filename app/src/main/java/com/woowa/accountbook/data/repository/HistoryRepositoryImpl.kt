package com.woowa.accountbook.data.repository

import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.data.local.history.HistoryDataSource
import com.woowa.accountbook.domain.repository.history.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(private val historyDataSource: HistoryDataSource): HistoryRepository {

    override fun getHistories(): Result<List<History>> {
        TODO("Not yet implemented")
    }

    override fun getHistoriesByMonth(month: Int): Result<List<History>> {
        return runCatching { historyDataSource.findByMonth(month.toString()) }
    }

    override fun getHistoriesMonthAndType(month: String, type: Boolean): Result<List<History>> {
        TODO("Not yet implemented")
    }

    override fun removeHistories(list: List<Int>) {
        TODO("Not yet implemented")
    }

    override fun saveHistory(
        money: Int,
        categoryId: Int,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    ) {
        TODO("Not yet implemented")
    }
}