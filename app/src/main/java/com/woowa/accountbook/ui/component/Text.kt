package com.woowa.accountbook.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowa.accountbook.ui.theme.Green2
import com.woowa.accountbook.ui.theme.Purple
import com.woowa.accountbook.ui.theme.Red
import com.woowa.accountbook.ui.theme.White

@Composable
fun LabelText(
    text: String,
    textStyle: TextStyle,
    color: Color,
) {
    Box(
        Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(color)
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Text(
            text = text,
            style = textStyle,
            color = White
        )
    }
}

@Composable
fun BothSideText(
    leftText: @Composable () -> Unit,
    rightText: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        leftText()
        rightText()
    }
}

@Preview(showBackground = true)
@Composable
private fun LabelTextPreview() {
    LabelText(
        text = "여가/문화",
        textStyle = MaterialTheme.typography.caption,
        color = Green2
    )
}

@Preview(showBackground = true)
@Composable
private fun BothSideTextPreview() {
    BothSideText(
        leftText = {
            Text(
                text = "이번 달 총 지출 금액",
                style = MaterialTheme.typography.subtitle2,
                color = Purple
            )
        },
        rightText = {
            Text(
                text = "834,640",
                style = MaterialTheme.typography.subtitle2,
                color = Red
            )
        }
    )
}