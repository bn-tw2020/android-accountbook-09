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

public val IconPack.Trash: ImageVector
    get() {
        if (_trash != null) {
            return _trash!!
        }
        _trash = Builder(name = "Trash", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFE75B3F)),
                    strokeLineWidth = 1.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(4.0f, 5.1428f)
                horizontalLineTo(20.0f)
                moveTo(9.7143f, 8.5714f)
                verticalLineTo(17.7143f)
                moveTo(14.2857f, 8.5714f)
                verticalLineTo(17.7143f)
                moveTo(6.2857f, 5.1428f)
                horizontalLineTo(17.7143f)
                verticalLineTo(18.8571f)
                curveTo(17.7143f, 19.4633f, 17.4735f, 20.0447f, 17.0448f, 20.4734f)
                curveTo(16.6162f, 20.902f, 16.0348f, 21.1428f, 15.4286f, 21.1428f)
                horizontalLineTo(8.5714f)
                curveTo(7.9652f, 21.1428f, 7.3838f, 20.902f, 6.9552f, 20.4734f)
                curveTo(6.5265f, 20.0447f, 6.2857f, 19.4633f, 6.2857f, 18.8571f)
                verticalLineTo(5.1428f)
                close()
                moveTo(12.0f, 2.8571f)
                curveTo(12.5767f, 2.8569f, 13.1321f, 3.0747f, 13.5549f, 3.4668f)
                curveTo(13.9777f, 3.8589f, 14.2368f, 4.3964f, 14.28f, 4.9714f)
                lineTo(14.2857f, 5.1428f)
                horizontalLineTo(9.7143f)
                curveTo(9.7143f, 4.5366f, 9.9551f, 3.9552f, 10.3838f, 3.5266f)
                curveTo(10.8124f, 3.0979f, 11.3938f, 2.8571f, 12.0f, 2.8571f)
                verticalLineTo(2.8571f)
                close()
            }
        }
        .build()
        return _trash!!
    }

private var _trash: ImageVector? = null
