package com.woowa.accountbook.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun Color.toHex(): String = String.format("#%06X", (0xFFFFFF and toArgb()))