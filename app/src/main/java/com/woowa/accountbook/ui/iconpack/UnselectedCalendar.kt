package com.woowa.accountbook.ui.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.woowa.accountbook.ui.IconPack

public val IconPack.UnselectedCalendar: ImageVector
    get() {
        if (_unselectedCalendar != null) {
            return _unselectedCalendar!!
        }
        _unselectedCalendar = Builder(name = "UnselectedCalendar", defaultWidth = 24.0.dp,
                defaultHeight = 24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeAlpha = 0.5f, strokeLineWidth = 1.0f, strokeLineCap = Round,
                    strokeLineJoin = StrokeJoin.Companion.Round, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(2.8571f, 7.4285f)
                horizontalLineTo(21.1428f)
                moveTo(5.1428f, 2.8571f)
                horizontalLineTo(18.8571f)
                curveTo(19.4633f, 2.8571f, 20.0447f, 3.0979f, 20.4734f, 3.5266f)
                curveTo(20.902f, 3.9552f, 21.1428f, 4.5366f, 21.1428f, 5.1428f)
                verticalLineTo(18.8571f)
                curveTo(21.1428f, 19.4633f, 20.902f, 20.0447f, 20.4734f, 20.4734f)
                curveTo(20.0447f, 20.902f, 19.4633f, 21.1428f, 18.8571f, 21.1428f)
                horizontalLineTo(5.1428f)
                curveTo(4.5366f, 21.1428f, 3.9552f, 20.902f, 3.5266f, 20.4734f)
                curveTo(3.0979f, 20.0447f, 2.8571f, 19.4633f, 2.8571f, 18.8571f)
                verticalLineTo(5.1428f)
                curveTo(2.8571f, 4.5366f, 3.0979f, 3.9552f, 3.5266f, 3.5266f)
                curveTo(3.9552f, 3.0979f, 4.5366f, 2.8571f, 5.1428f, 2.8571f)
                verticalLineTo(2.8571f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, fillAlpha = 0.5f,
                    strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(12.0f, 13.1428f)
                curveTo(12.6312f, 13.1428f, 13.1428f, 12.6312f, 13.1428f, 12.0f)
                curveTo(13.1428f, 11.3688f, 12.6312f, 10.8571f, 12.0f, 10.8571f)
                curveTo(11.3688f, 10.8571f, 10.8571f, 11.3688f, 10.8571f, 12.0f)
                curveTo(10.8571f, 12.6312f, 11.3688f, 13.1428f, 12.0f, 13.1428f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, fillAlpha = 0.5f,
                    strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(7.4286f, 13.1428f)
                curveTo(8.0597f, 13.1428f, 8.5714f, 12.6312f, 8.5714f, 12.0f)
                curveTo(8.5714f, 11.3688f, 8.0597f, 10.8571f, 7.4286f, 10.8571f)
                curveTo(6.7974f, 10.8571f, 6.2857f, 11.3688f, 6.2857f, 12.0f)
                curveTo(6.2857f, 12.6312f, 6.7974f, 13.1428f, 7.4286f, 13.1428f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, fillAlpha = 0.5f,
                    strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(16.5714f, 13.1428f)
                curveTo(17.2026f, 13.1428f, 17.7143f, 12.6312f, 17.7143f, 12.0f)
                curveTo(17.7143f, 11.3688f, 17.2026f, 10.8571f, 16.5714f, 10.8571f)
                curveTo(15.9403f, 10.8571f, 15.4286f, 11.3688f, 15.4286f, 12.0f)
                curveTo(15.4286f, 12.6312f, 15.9403f, 13.1428f, 16.5714f, 13.1428f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, fillAlpha = 0.5f,
                    strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(12.0f, 17.7142f)
                curveTo(12.6312f, 17.7142f, 13.1428f, 17.2026f, 13.1428f, 16.5714f)
                curveTo(13.1428f, 15.9402f, 12.6312f, 15.4285f, 12.0f, 15.4285f)
                curveTo(11.3688f, 15.4285f, 10.8571f, 15.9402f, 10.8571f, 16.5714f)
                curveTo(10.8571f, 17.2026f, 11.3688f, 17.7142f, 12.0f, 17.7142f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, fillAlpha = 0.5f,
                    strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(7.4286f, 17.7142f)
                curveTo(8.0597f, 17.7142f, 8.5714f, 17.2026f, 8.5714f, 16.5714f)
                curveTo(8.5714f, 15.9402f, 8.0597f, 15.4285f, 7.4286f, 15.4285f)
                curveTo(6.7974f, 15.4285f, 6.2857f, 15.9402f, 6.2857f, 16.5714f)
                curveTo(6.2857f, 17.2026f, 6.7974f, 17.7142f, 7.4286f, 17.7142f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, fillAlpha = 0.5f,
                    strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(16.5714f, 17.7142f)
                curveTo(17.2026f, 17.7142f, 17.7143f, 17.2026f, 17.7143f, 16.5714f)
                curveTo(17.7143f, 15.9402f, 17.2026f, 15.4285f, 16.5714f, 15.4285f)
                curveTo(15.9403f, 15.4285f, 15.4286f, 15.9402f, 15.4286f, 16.5714f)
                curveTo(15.4286f, 17.2026f, 15.9403f, 17.7142f, 16.5714f, 17.7142f)
                close()
            }
        }
        .build()
        return _unselectedCalendar!!
    }

private var _unselectedCalendar: ImageVector? = null
