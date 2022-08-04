package com.woowa.accountbook.domain.repository.payment

import com.woowa.accountbook.data.entitiy.Payment

interface PaymentRepository {
    suspend fun getPayments(): Result<List<Payment>>
    suspend fun removePayments(list: List<Int>): Result<Boolean>
    suspend fun updatePayment(id: Int, name: String)
    suspend fun savePayment(name: String)
}