package com.woowa.accountbook.data.local.payment

import com.woowa.accountbook.data.entitiy.Payment

interface PaymentDataSource {

    fun findAll(): List<Payment>
    fun deleteById(list: List<Int>)
    fun save(name: String)
}