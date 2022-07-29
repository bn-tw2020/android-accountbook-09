package com.woowa.accountbook.domain.repository.payment

import com.woowa.accountbook.data.entitiy.Payment

interface PaymentRepository {
    fun getPayments(): Result<List<Payment>>
    fun removePayments(list: List<Int>)
    fun savePayment(name: String)
}