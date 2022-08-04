package com.woowa.accountbook.ui

import androidx.compose.ui.graphics.vector.ImageVector
import com.woowa.accountbook.R
import com.woowa.accountbook.ui.iconpack.*


interface HomeSections {
    val title: Int
    val icon: ImageVector
    val route: String
}

interface Destinations {
    val route: String
}

object History : HomeSections {
    override val title: Int
        get() = R.string.history
    override val icon: ImageVector
        get() = IconPack.History
    override val route: String
        get() = "home/history"

}

object Calendar : HomeSections {
    override val title: Int
        get() = R.string.calendar
    override val icon: ImageVector
        get() = IconPack.Calendar
    override val route: String
        get() = "home/calendar"

}

object Statistics : HomeSections {
    override val title: Int
        get() = R.string.statistics
    override val icon: ImageVector
        get() = IconPack.Statistics
    override val route: String
        get() = "home/statistics"
}

object Setting : HomeSections {
    override val title: Int
        get() = R.string.setting
    override val icon: ImageVector
        get() = IconPack.Setting
    override val route: String
        get() = "home/setting"
}

object Registration : Destinations {
    override val route: String
        get() = "home/history/registration"
}

object StatisticsDetail : Destinations {
    override val route: String
        get() = "home/statistics/detail"
}

object SettingRegistration : Destinations {
    override val route: String
        get() = "home/setting/registration"
}