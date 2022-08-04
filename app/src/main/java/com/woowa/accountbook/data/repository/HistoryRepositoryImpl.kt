package com.woowa.accountbook.data.repository

import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.data.local.category.CategoryDataSource
import com.woowa.accountbook.data.local.history.HistoryDataSource
import com.woowa.accountbook.data.local.payment.PaymentDataSource
import com.woowa.accountbook.domain.repository.history.HistoryRepository
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource,
    private val categoryDataSource: CategoryDataSource,
    private val paymentDataSource: PaymentDataSource
) : HistoryRepository {

    override suspend fun getHistory(id: Int): Result<History?> {
        return runCatching {
            historyDataSource.findById(id)
        }
    }

    override suspend fun getExpenseHistories(year: Int): Result<List<History>> {
        return runCatching {
            val yearHistories = historyDataSource.findByAll(year)
            yearHistories.filter { it.category?.isIncome == 0 }
        }
    }

    override suspend fun getHistoriesByMonth(month: Int): Result<List<History>> {
        return runCatching {
            historyDataSource.findByMonth(month.toString())
        }
    }

    override suspend fun getHistoriesMonthAndType(
        month: Int,
        type: Boolean
    ): Result<List<History>> {
        return runCatching {
            historyDataSource.findByMonthAndType(month.toString(), type)
        }
    }

    override suspend fun removeHistories(list: List<Int>) {
        runCatching {
            historyDataSource.deleteById(list)
        }
    }

    override suspend fun updateHistory(
        id: Int,
        money: Int,
        categoryId: Int?,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    ) {
        runCatching {
            historyDataSource.update(
                id,
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

    override suspend fun saveHistory(
        money: Int,
        categoryId: Int?,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    ) {
        runCatching {
            if (categoryId != null) {
                val category = categoryDataSource.findById(categoryId) ?: return
            }
            val payment = paymentDataSource.findById(paymentId) ?: return
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