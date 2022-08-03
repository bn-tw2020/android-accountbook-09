package com.woowa.accountbook.data.local.payment

import com.woowa.accountbook.data.entitiy.Payment

interface PaymentDataSource {

    fun findById(id: Int): Payment?
    fun findByName(name: String): Payment?
    fun findAll(): List<Payment>
    fun deleteById(list: List<Int>)
    fun update(id: Int, name: String)
    fun save(name: String)
}