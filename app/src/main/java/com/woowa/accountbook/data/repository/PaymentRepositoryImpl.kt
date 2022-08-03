package com.woowa.accountbook.data.repository

import com.woowa.accountbook.data.entitiy.Payment
import com.woowa.accountbook.data.local.history.HistoryDataSource
import com.woowa.accountbook.data.local.payment.PaymentDataSource
import com.woowa.accountbook.domain.repository.payment.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource,
    private val paymentDataSource: PaymentDataSource
) : PaymentRepository {

    override fun getPayments(): Result<List<Payment>> {
        return runCatching { paymentDataSource.findAll() }
    }

    override fun removePayments(list: List<Int>): Result<Boolean> {
        return runCatching {
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

    override fun updatePayment(id: Int, name: String) {
        runCatching {
            paymentDataSource.update(id, name)
        }
    }

    override fun savePayment(name: String) {
        runCatching {
            val payment = paymentDataSource.findByName(name)
            if (payment == null)
                paymentDataSource.save(name)
        }
    }
}