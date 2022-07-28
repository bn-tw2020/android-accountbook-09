package com.woowa.accountbook.ui.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val IconPack.History: ImageVector
    get() {
        if (_history != null) {
            return _history!!
        }
        _history = Builder(
            name = "History", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                strokeLineWidth = 1.0f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(10.5321f, 8.2257f)
                horizontalLineTo(17.0643f)
                moveTo(6.6129f, 8.2257f)
                horizontalLineTo(7.9193f)
                moveTo(10.5321f, 12.145f)
                horizontalLineTo(17.0643f)
                moveTo(6.6129f, 12.145f)
                horizontalLineTo(7.9193f)
                moveTo(10.5321f, 16.0643f)
                horizontalLineTo(17.0643f)
                moveTo(6.6129f, 16.0643f)
                horizontalLineTo(7.9193f)
                moveTo(19.6771f, 18.6771f)
                verticalLineTo(5.6129f)
                curveTo(19.6771f, 4.9199f, 19.4019f, 4.2553f, 18.9119f, 3.7653f)
                curveTo(18.4218f, 3.2753f, 17.7573f, 3.0f, 17.0643f, 3.0f)
                horizontalLineTo(6.6129f)
                curveTo(5.9199f, 3.0f, 5.2553f, 3.2753f, 4.7653f, 3.7653f)
                curveTo(4.2753f, 4.2553f, 4.0f, 4.9199f, 4.0f, 5.6129f)
                verticalLineTo(18.6771f)
                curveTo(4.0f, 19.3701f, 4.2753f, 20.0347f, 4.7653f, 20.5247f)
                curveTo(5.2553f, 21.0147f, 5.9199f, 21.29f, 6.6129f, 21.29f)
                horizontalLineTo(17.0643f)
                curveTo(17.7573f, 21.29f, 18.4218f, 21.0147f, 18.9119f, 20.5247f)
                curveTo(19.4019f, 20.0347f, 19.6771f, 19.3701f, 19.6771f, 18.6771f)
                verticalLineTo(18.6771f)
                close()
            }
        }
            .build()
        return _history!!
    }

private var _history: ImageVector? = null
