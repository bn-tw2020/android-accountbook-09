package com.woowa.accountbook.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowa.accountbook.ui.iconpack.IconPack
import com.woowa.accountbook.ui.iconpack.LeftArrow
import com.woowa.accountbook.ui.iconpack.RightArrow
import com.woowa.accountbook.ui.theme.OffWhite
import com.woowa.accountbook.ui.theme.Purple
import com.woowa.accountbook.ui.theme.Typography

@Composable
fun AccountBookAppBar(
    title: String,
    navigationIcon: ImageVector?,
    onNavigationClicked: () -> Unit,
    actionIcon: ImageVector?,
    onActionClicked: () -> Unit,
    backgroundColor: Color = OffWhite,
    contentColor: Color = Purple
) {
    TopAppBar(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        title = {
            Text(
                modifier = when (actionIcon) {
                    null -> {
                        Modifier
                            .fillMaxWidth()
                            .padding(end = 72.dp - 4.dp)
                    }
                    else -> Modifier.fillMaxWidth()
                },
                textAlign = TextAlign.Center,
                style = Typography.subtitle1,
                maxLines = 1,
                text = title
            )
        },
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = { onNavigationClicked() }) {
                    Icon(imageVector = navigationIcon, contentDescription = "navigation_icon")
                }
            }
        },
        actions = {
            if (actionIcon != null) {
                IconButton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp - 4.dp),
                    onClick = { onActionClicked() }
                ) {
                    Icon(imageVector = actionIcon, contentDescription = "action_icon")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AccountBookAppBarPreview() {
    AccountBookAppBar(
        title = "2022년 7월",
        navigationIcon = IconPack.LeftArrow,
        onNavigationClicked = {},
        actionIcon = IconPack.RightArrow,
        onActionClicked = {}
    )
}

@Preview(showBackground = true)
@Composable
fun AccountBookRegistrationAppBar() {
    AccountBookAppBar(
        title = "결제 수단 추가하기",
        navigationIcon = IconPack.LeftArrow,
        onNavigationClicked = {},
        actionIcon = null,
        onActionClicked = {}
    )
}

@Preview(showBackground = true)
@Composable
fun AccountBookSettingAppBar() {
    AccountBookAppBar(
        title = "설정",
        navigationIcon = null,
        onNavigationClicked = {},
        actionIcon = null,
        onActionClicked = {}
    )
}