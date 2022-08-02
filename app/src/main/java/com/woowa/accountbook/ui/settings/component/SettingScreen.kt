package com.woowa.accountbook.ui.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.woowa.accountbook.ui.component.AccountBookAppBar
import com.woowa.accountbook.ui.theme.LightPurple
import com.woowa.accountbook.ui.theme.OffWhite

@Composable
fun SettingScreen() {
    Scaffold(
        backgroundColor = OffWhite,
        topBar = {
            AccountBookAppBar(title = "설정")
        }
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(LightPurple)
        )
    }
}