package com.woowa.accountbook.ui.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val IconPack.Checkbox: ImageVector
    get() {
        if (_checkbox != null) {
            return _checkbox!!
        }
        _checkbox = Builder(
            name = "Checkbox", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFE75B3F)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(4.653f, 1.7143f)
                horizontalLineTo(19.3469f)
                curveTo(20.1263f, 1.7143f, 20.8738f, 2.0239f, 21.425f, 2.575f)
                curveTo(21.9761f, 3.1262f, 22.2857f, 3.8737f, 22.2857f, 4.6531f)
                verticalLineTo(19.3469f)
                curveTo(22.2857f, 20.1264f, 21.9761f, 20.8738f, 21.425f, 21.425f)
                curveTo(20.8738f, 21.9761f, 20.1263f, 22.2857f, 19.3469f, 22.2857f)
                horizontalLineTo(4.653f)
                curveTo(3.8736f, 22.2857f, 3.1261f, 21.9761f, 2.575f, 21.425f)
                curveTo(2.0239f, 20.8738f, 1.7143f, 20.1264f, 1.7143f, 19.3469f)
                verticalLineTo(4.6531f)
                curveTo(1.7143f, 3.8737f, 2.0239f, 3.1262f, 2.575f, 2.575f)
                curveTo(3.1261f, 2.0239f, 3.8736f, 1.7143f, 4.653f, 1.7143f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                strokeLineWidth = 1.0f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(7.5918f, 12.0f)
                lineTo(10.5306f, 14.9388f)
                lineTo(16.4082f, 9.0612f)
            }
        }
            .build()
        return _checkbox!!
    }

private var _checkbox: ImageVector? = null
