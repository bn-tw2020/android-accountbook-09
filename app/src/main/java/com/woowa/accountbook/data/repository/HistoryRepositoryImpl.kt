package com.woowa.accountbook.data.repository

import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.data.local.history.HistoryDataSource
import com.woowa.accountbook.domain.repository.history.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource
) : HistoryRepository {

    override fun getHistories(): Result<List<History>> {
        return runCatching { historyDataSource.findByAll() }
    }

    override fun getHistoriesByMonth(month: Int): Result<List<History>> {
        return runCatching { historyDataSource.findByMonth(month.toString()) }
    }

    override fun getHistoriesMonthAndType(month: Int, type: Boolean): Result<List<History>> {
        return runCatching { historyDataSource.findByMonthAndType(month.toString(), type) }
    }

    override fun removeHistories(list: List<Int>) {
        runCatching { historyDataSource.deleteById(list) }
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
        runCatching {
            historyDataSource.save(
                money,
                categoryId,
                content,
                year,
                month,
                day,
                paymentId
            )
        }
    }
}