package com.woowa.accountbook.domain.entity

import androidx.compose.ui.graphics.Color

data class DateItem(
    val expense: Int?,
    val income: Int?,
    val year: Int,
    val month: Int,
    val date: Int,
    val color: Color
)