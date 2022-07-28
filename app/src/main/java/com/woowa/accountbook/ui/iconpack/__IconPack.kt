package com.woowa.accountbook.ui.iconpack

import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.collections.List as ____KtList

object IconPack

private var __AllIcons: ____KtList<ImageVector>? = null

val IconPack.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(
            Uncheckbox,
            UnselectedHistory,
            UnselectedCalendar,
            Down,
            UnselectedStatistics,
            RightArrow,
            LeftArrow,
            Plus,
            Check,
            UnselectedSetting,
            Setting,
            Back,
            Trash,
            Checkbox,
            Calendar,
            Statistics,
            History
        )
        return __AllIcons!!
    }
