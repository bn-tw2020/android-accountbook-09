package com.woowa.accountbook.data.repository

import com.woowa.accountbook.data.entitiy.Payment
import com.woowa.accountbook.data.local.history.HistoryDataSource
import com.woowa.accountbook.data.local.payment.PaymentDataSource
import com.woowa.accountbook.domain.repository.payment.PaymentRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource,
    private val paymentDataSource: PaymentDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : PaymentRepository {

    override suspend fun getPayments(): Result<List<Payment>> {
        return runCatching {
            withContext(ioDispatcher) {
                paymentDataSource.findAll()
            }
        }
    }

    override suspend fun removePayments(list: List<Int>): Result<Boolean> {
        return runCatching {
            withContext(ioDispatcher) {
                val result = list.asSequence()
                    .map { id -> historyDataSource.existsByPaymentId(id) }
                    .find { !it }
                if (result == false) {
                    paymentDataSource.deleteById(list)
                    true
                } else {
                    false
                }
            }
        }
    }

    override suspend fun updatePayment(id: Int, name: String) {
        runCatching {
            withContext(ioDispatcher) {
                paymentDataSource.update(id, name)
            }
        }
    }

    override suspend fun savePayment(name: String) {
        runCatching {
            withContext(ioDispatcher) {
                val payment = paymentDataSource.findByName(name)
                if (payment == null)
                    paymentDataSource.save(name)
            }
        }
    }
}