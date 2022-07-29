package com.woowa.accountbook.data.repository

import com.woowa.accountbook.data.entitiy.Payment
import com.woowa.accountbook.data.local.payment.PaymentDataSource
import com.woowa.accountbook.domain.repository.payment.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val paymentDataSource: PaymentDataSource
) : PaymentRepository {

    override fun getPayments(): Result<List<Payment>> {
        TODO("Not yet implemented")
    }

    override fun removePayments(list: List<Int>) {
        TODO("Not yet implemented")
    }

    override fun savePayment(name: String) {
        TODO("Not yet implemented")
    }
}