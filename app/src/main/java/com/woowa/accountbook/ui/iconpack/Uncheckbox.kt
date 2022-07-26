package com.woowa.accountbook.ui.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.woowa.accountbook.ui.IconPack

public val IconPack.Uncheckbox: ImageVector
    get() {
        if (_uncheckbox != null) {
            return _uncheckbox!!
        }
        _uncheckbox = Builder(name = "Uncheckbox", defaultWidth = 22.0.dp, defaultHeight = 22.0.dp,
                viewportWidth = 22.0f, viewportHeight = 22.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF524D90)),
                    strokeLineWidth = 1.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(3.653f, 0.7143f)
                horizontalLineTo(18.3469f)
                curveTo(19.1263f, 0.7143f, 19.8738f, 1.0239f, 20.425f, 1.575f)
                curveTo(20.9761f, 2.1262f, 21.2857f, 2.8737f, 21.2857f, 3.6531f)
                verticalLineTo(18.3469f)
                curveTo(21.2857f, 19.1264f, 20.9761f, 19.8738f, 20.425f, 20.425f)
                curveTo(19.8738f, 20.9761f, 19.1263f, 21.2857f, 18.3469f, 21.2857f)
                horizontalLineTo(3.653f)
                curveTo(2.8736f, 21.2857f, 2.1261f, 20.9761f, 1.575f, 20.425f)
                curveTo(1.0239f, 19.8738f, 0.7143f, 19.1264f, 0.7143f, 18.3469f)
                verticalLineTo(3.6531f)
                curveTo(0.7143f, 2.8737f, 1.0239f, 2.1262f, 1.575f, 1.575f)
                curveTo(2.1261f, 1.0239f, 2.8736f, 0.7143f, 3.653f, 0.7143f)
                verticalLineTo(0.7143f)
                close()
            }
        }
        .build()
        return _uncheckbox!!
    }

private var _uncheckbox: ImageVector? = null
