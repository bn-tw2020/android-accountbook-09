package com.woowa.accountbook.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowa.accountbook.ui.history.component.MoneyVisualTransformation
import com.woowa.accountbook.ui.theme.*

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
            .width(65.dp)
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
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

@Composable
fun InputText(
    label: String,
    content: @Composable () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
    ) {
        Text(
            modifier = Modifier.weight(2f),
            text = label,
            style = MaterialTheme.typography.body2,
            color = Purple
        )
        Row(modifier = Modifier.weight(9f)) { content() }
    }
    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Purple40)
}

@Composable
fun InputDateText(
    text: String,
    dialog: @Composable (Boolean, (Boolean) -> Unit) -> Unit = { _, _ -> }
) {
    var isShowDialog by remember { mutableStateOf(false) }
    Text(
        text = text,
        color = Purple,
        style = MaterialTheme.typography.body2,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isShowDialog = !isShowDialog }
    )
    dialog(isShowDialog) { isShowDialog = !it }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputNumberText(
    price: String,
    onChanged: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    BasicTextField(
        value = price,
        onValueChange = {
            if (it == "") {
                onChanged("")
                return@BasicTextField
            }
            val amount = it.replace(("[^\\d.]").toRegex(), "")
            onChanged(amount)
        },
        decorationBox = { innerTextField ->
            if (price.isEmpty()) {
                Text(
                    "입력해주세요",
                    style = MaterialTheme.typography.body2,
                    color = Purple
                )
            }
            innerTextField()
        },
        textStyle = TextStyle(
            color = Purple,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.None
        ),
        enabled = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        ),
        visualTransformation = MoneyVisualTransformation()
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputContentText(
    content: String,
    onChanged: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    BasicTextField(
        value = content,
        onValueChange = { onChanged(it) },
        decorationBox = { innerTextField ->
            if (content.isEmpty()) {
                Text(
                    "입력해주세요",
                    style = MaterialTheme.typography.body2,
                    color = Purple
                )
            }
            innerTextField()
        },
        textStyle = TextStyle(
            color = Purple,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.None
        ),
        enabled = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )
    )
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