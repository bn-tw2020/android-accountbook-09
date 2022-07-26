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
import com.woowa.accountbook.ui.IconPack

public val IconPack.UnselectedStatistics: ImageVector
    get() {
        if (_unselectedStatistics != null) {
            return _unselectedStatistics!!
        }
        _unselectedStatistics = Builder(name = "UnselectedStatistics", defaultWidth = 24.0.dp,
                defaultHeight = 24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeAlpha = 0.5f, strokeLineWidth = 1.0f, strokeLineCap = Round,
                    strokeLineJoin = StrokeJoin.Companion.Round, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.5714f, 16.4971f)
                verticalLineTo(7.4286f)
                moveTo(12.0f, 16.5543f)
                verticalLineTo(12.0f)
                moveTo(15.4286f, 16.5714f)
                verticalLineTo(9.7143f)
                moveTo(6.2857f, 4.0f)
                horizontalLineTo(17.7143f)
                curveTo(18.3205f, 4.0f, 18.9019f, 4.2408f, 19.3305f, 4.6695f)
                curveTo(19.7592f, 5.0981f, 20.0f, 5.6795f, 20.0f, 6.2857f)
                verticalLineTo(17.7143f)
                curveTo(20.0f, 18.3205f, 19.7592f, 18.9019f, 19.3305f, 19.3305f)
                curveTo(18.9019f, 19.7592f, 18.3205f, 20.0f, 17.7143f, 20.0f)
                horizontalLineTo(6.2857f)
                curveTo(5.6795f, 20.0f, 5.0981f, 19.7592f, 4.6695f, 19.3305f)
                curveTo(4.2408f, 18.9019f, 4.0f, 18.3205f, 4.0f, 17.7143f)
                verticalLineTo(6.2857f)
                curveTo(4.0f, 5.6795f, 4.2408f, 5.0981f, 4.6695f, 4.6695f)
                curveTo(5.0981f, 4.2408f, 5.6795f, 4.0f, 6.2857f, 4.0f)
                verticalLineTo(4.0f)
                close()
            }
        }
        .build()
        return _unselectedStatistics!!
    }

private var _unselectedStatistics: ImageVector? = null
