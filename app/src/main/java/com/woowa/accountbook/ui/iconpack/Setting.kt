package com.woowa.accountbook.ui.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val IconPack.Setting: ImageVector
    get() {
        if (_setting != null) {
            return _setting!!
        }
        _setting = Builder(
            name = "Setting", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                strokeLineWidth = 0.933f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = EvenOdd
            ) {
                moveTo(12.0f, 4.0f)
                curveTo(12.4011f, 4.0f, 12.7966f, 4.0297f, 13.1817f, 4.0869f)
                lineTo(13.7623f, 5.8457f)
                curveTo(14.2708f, 5.9909f, 14.7543f, 6.1977f, 15.2023f, 6.4583f)
                lineTo(16.8709f, 5.6537f)
                curveTo(17.4617f, 6.1074f, 17.9874f, 6.6423f, 18.4308f, 7.2411f)
                lineTo(17.5966f, 8.8949f)
                curveTo(17.8491f, 9.3474f, 18.048f, 9.8343f, 18.184f, 10.3463f)
                lineTo(19.9326f, 10.9554f)
                curveTo(20.0289f, 11.6949f, 20.0224f, 12.4441f, 19.9131f, 13.1817f)
                lineTo(18.1543f, 13.7623f)
                curveTo(18.0101f, 14.2657f, 17.8044f, 14.7493f, 17.5417f, 15.2023f)
                lineTo(18.3463f, 16.8709f)
                curveTo(17.892f, 17.4619f, 17.3577f, 17.9869f, 16.7588f, 18.4309f)
                lineTo(15.1051f, 17.5966f)
                curveTo(14.6477f, 17.852f, 14.1601f, 18.0494f, 13.6537f, 18.184f)
                lineTo(13.0446f, 19.9326f)
                curveTo(12.3051f, 20.0289f, 11.5559f, 20.0224f, 10.8183f, 19.9131f)
                lineTo(10.2377f, 18.1543f)
                curveTo(9.7343f, 18.0101f, 9.2507f, 17.8044f, 8.7977f, 17.5417f)
                lineTo(7.1291f, 18.3463f)
                curveTo(6.5381f, 17.892f, 6.0131f, 17.3577f, 5.5691f, 16.7589f)
                lineTo(6.4034f, 15.1051f)
                curveTo(6.1486f, 14.6474f, 5.9513f, 14.1599f, 5.816f, 13.6537f)
                lineTo(4.0674f, 13.0434f)
                curveTo(3.9712f, 12.3044f, 3.9777f, 11.5556f, 4.0869f, 10.8183f)
                lineTo(5.8457f, 10.2377f)
                curveTo(5.9908f, 9.7291f, 6.1977f, 9.2457f, 6.4583f, 8.7977f)
                lineTo(5.6537f, 7.1291f)
                curveTo(6.108f, 6.5381f, 6.6423f, 6.0131f, 7.2411f, 5.5691f)
                lineTo(8.8948f, 6.4034f)
                curveTo(9.3523f, 6.148f, 9.8399f, 5.9506f, 10.3463f, 5.816f)
                lineTo(10.9566f, 4.0674f)
                curveTo(11.3026f, 4.0224f, 11.6511f, 3.9999f, 12.0f, 4.0f)
                verticalLineTo(4.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                strokeLineWidth = 1.0f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(12.0f, 15.4286f)
                curveTo(13.8935f, 15.4286f, 15.4286f, 13.8936f, 15.4286f, 12.0f)
                curveTo(15.4286f, 10.1065f, 13.8935f, 8.5714f, 12.0f, 8.5714f)
                curveTo(10.1064f, 8.5714f, 8.5714f, 10.1065f, 8.5714f, 12.0f)
                curveTo(8.5714f, 13.8936f, 10.1064f, 15.4286f, 12.0f, 15.4286f)
                close()
            }
        }
            .build()
        return _setting!!
    }

private var _setting: ImageVector? = null
