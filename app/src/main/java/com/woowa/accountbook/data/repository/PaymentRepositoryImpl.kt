package com.woowa.accountbook.data.repository

import com.woowa.accountbook.data.entitiy.Payment
import com.woowa.accountbook.data.local.payment.PaymentDataSource
import com.woowa.accountbook.domain.repository.payment.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val paymentDataSource: PaymentDataSource
) : PaymentRepository {

    override fun getPayments(): Result<List<Payment>> {
        return runCatching { paymentDataSource.findAll() }
    }

    override fun removePayments(list: List<Int>) {
        TODO("Not yet implemented")
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