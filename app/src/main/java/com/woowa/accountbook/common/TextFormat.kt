package com.woowa.accountbook.common

import java.text.DecimalFormat

fun rawToMoneyFormat(money: Int, isIncome: Int): String {
    if (money == 0) return ""
    val decimalFormat = DecimalFormat("#,###")
    val result = decimalFormat.format(money)
    return if (isIncome == 1) result else "-$result"
}

fun rawToYearAndMonth(year: Int, month: Int): String {
    return "${year}년 ${month}월"
}