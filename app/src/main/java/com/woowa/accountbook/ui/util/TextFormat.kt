package com.woowa.accountbook.ui.util

import java.text.DecimalFormat

fun rawToMoneyFormat(money: Int, isIncome: Int): String {
    if(money == 0) return "0"
    val decimalFormat = DecimalFormat("#,###")
    val result = decimalFormat.format(money)
    return if (isIncome == 1) result else "-$result"
}