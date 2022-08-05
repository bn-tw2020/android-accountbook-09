package com.woowa.accountbook.common

import java.text.DecimalFormat

fun rawToMoneyFormat(money: Int, isIncome: Int?): String {
    if (money == 0) return ""
    val result = stringToMoneyFormat(money)
    return if (isIncome == 1) result else "-$result"
}

fun stringToMoneyFormat(money: Int): String {
    val decimalFormat = DecimalFormat("#,###")
    return decimalFormat.format(money)
}

fun rawToYearAndMonth(year: Int, month: Int): String {
    return "${year}년 ${month}월"
}