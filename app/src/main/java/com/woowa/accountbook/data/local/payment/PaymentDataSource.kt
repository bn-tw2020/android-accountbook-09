package com.woowa.accountbook.data.local.payment

import com.woowa.accountbook.data.entitiy.Payment

interface PaymentDataSource {

    suspend fun findById(id: Int): Payment?
    suspend fun findByName(name: String): Payment?
    suspend fun findAll(): List<Payment>
    suspend fun deleteById(list: List<Int>)
    suspend fun update(id: Int, name: String)
    suspend fun save(name: String)
}