package com.woowa.accountbook.data.entitiy

data class History(
    val id: Int,
    val money: Int,
    val content: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val category: Category,
    val payment: Payment,
    var isChecked: Boolean = false
)