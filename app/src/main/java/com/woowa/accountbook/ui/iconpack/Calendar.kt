package com.woowa.accountbook.ui.iconpack

import androidx.compose.ui.graphics.Color
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

public val IconPack.Calendar: ImageVector
    get() {
        if (_calendar != null) {
            return _calendar!!
        }
        _calendar = Builder(
            name = "Calendar", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                strokeLineWidth = 1.0f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(2.8572f, 7.4286f)
                horizontalLineTo(21.1429f)
                moveTo(5.1429f, 2.8572f)
                horizontalLineTo(18.8571f)
                curveTo(19.4634f, 2.8572f, 20.0447f, 3.098f, 20.4734f, 3.5266f)
                curveTo(20.902f, 3.9553f, 21.1429f, 4.5367f, 21.1429f, 5.1429f)
                verticalLineTo(18.8571f)
                curveTo(21.1429f, 19.4634f, 20.902f, 20.0447f, 20.4734f, 20.4734f)
                curveTo(20.0447f, 20.902f, 19.4634f, 21.1429f, 18.8571f, 21.1429f)
                horizontalLineTo(5.1429f)
                curveTo(4.5367f, 21.1429f, 3.9553f, 20.902f, 3.5266f, 20.4734f)
                curveTo(3.098f, 20.0447f, 2.8572f, 19.4634f, 2.8572f, 18.8571f)
                verticalLineTo(5.1429f)
                curveTo(2.8572f, 4.5367f, 3.098f, 3.9553f, 3.5266f, 3.5266f)
                curveTo(3.9553f, 3.098f, 4.5367f, 2.8572f, 5.1429f, 2.8572f)
                verticalLineTo(2.8572f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 13.1429f)
                curveTo(12.6312f, 13.1429f, 13.1429f, 12.6312f, 13.1429f, 12.0f)
                curveTo(13.1429f, 11.3688f, 12.6312f, 10.8571f, 12.0f, 10.8571f)
                curveTo(11.3688f, 10.8571f, 10.8571f, 11.3688f, 10.8571f, 12.0f)
                curveTo(10.8571f, 12.6312f, 11.3688f, 13.1429f, 12.0f, 13.1429f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(7.4286f, 13.1429f)
                curveTo(8.0598f, 13.1429f, 8.5714f, 12.6312f, 8.5714f, 12.0f)
                curveTo(8.5714f, 11.3688f, 8.0598f, 10.8571f, 7.4286f, 10.8571f)
                curveTo(6.7974f, 10.8571f, 6.2857f, 11.3688f, 6.2857f, 12.0f)
                curveTo(6.2857f, 12.6312f, 6.7974f, 13.1429f, 7.4286f, 13.1429f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(16.5714f, 13.1429f)
                curveTo(17.2026f, 13.1429f, 17.7143f, 12.6312f, 17.7143f, 12.0f)
                curveTo(17.7143f, 11.3688f, 17.2026f, 10.8571f, 16.5714f, 10.8571f)
                curveTo(15.9402f, 10.8571f, 15.4286f, 11.3688f, 15.4286f, 12.0f)
                curveTo(15.4286f, 12.6312f, 15.9402f, 13.1429f, 16.5714f, 13.1429f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 17.7143f)
                curveTo(12.6312f, 17.7143f, 13.1429f, 17.2026f, 13.1429f, 16.5714f)
                curveTo(13.1429f, 15.9403f, 12.6312f, 15.4286f, 12.0f, 15.4286f)
                curveTo(11.3688f, 15.4286f, 10.8571f, 15.9403f, 10.8571f, 16.5714f)
                curveTo(10.8571f, 17.2026f, 11.3688f, 17.7143f, 12.0f, 17.7143f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(7.4286f, 17.7143f)
                curveTo(8.0598f, 17.7143f, 8.5714f, 17.2026f, 8.5714f, 16.5714f)
                curveTo(8.5714f, 15.9403f, 8.0598f, 15.4286f, 7.4286f, 15.4286f)
                curveTo(6.7974f, 15.4286f, 6.2857f, 15.9403f, 6.2857f, 16.5714f)
                curveTo(6.2857f, 17.2026f, 6.7974f, 17.7143f, 7.4286f, 17.7143f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(16.5714f, 17.7143f)
                curveTo(17.2026f, 17.7143f, 17.7143f, 17.2026f, 17.7143f, 16.5714f)
                curveTo(17.7143f, 15.9403f, 17.2026f, 15.4286f, 16.5714f, 15.4286f)
                curveTo(15.9402f, 15.4286f, 15.4286f, 15.9403f, 15.4286f, 16.5714f)
                curveTo(15.4286f, 17.2026f, 15.9402f, 17.7143f, 16.5714f, 17.7143f)
                close()
            }
        }
            .build()
        return _calendar!!
    }

private var _calendar: ImageVector? = null
