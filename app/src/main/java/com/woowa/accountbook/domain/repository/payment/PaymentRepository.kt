package com.woowa.accountbook.domain.repository.payment

import com.woowa.accountbook.data.entitiy.Payment

interface PaymentRepository {
    fun getPayments(): Result<List<Payment>>
    fun removePayments(list: List<Int>)
    fun updatePayment(id: Int, name: String)
    fun savePayment(name: String)
}