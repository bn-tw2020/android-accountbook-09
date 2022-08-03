package com.woowa.accountbook.data.entitiy

data class Payment(
    val id: Int,
    val name: String,
    var isChecked: Boolean = false
)