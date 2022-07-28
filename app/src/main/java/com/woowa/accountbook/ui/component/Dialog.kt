package com.woowa.accountbook.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chargemap.compose.numberpicker.NumberPicker
import com.woowa.accountbook.ui.theme.White
import com.woowa.accountbook.ui.theme.Yellow

@Composable
fun AccountBookDialog(
    isShow: Boolean,
    onDismissRequest: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (Boolean) -> Unit = {}
) {
    if (isShow) {
        Dialog(onDismissRequest = { onDismissRequest(isShow) }) {
            Surface(
                modifier = modifier,
                color = White
            ) {
                content(isShow)
            }
        }
    }
}

@Composable
fun YearAndMonthPicker(
    year: Int,
    month: Int,
    onClicked: (Int, Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var selectedYear = year
        var selectedMonth = month
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DateNumberPicker(1000..9999, year, onCompleted = { selectedYear = it })
            Spacer(modifier = Modifier.width(30.dp))
            DateNumberPicker(1..12, month, onCompleted = { selectedMonth = it })
        }
        TextButton(
            text = "완료",
            textColor = White,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            onClicked = {
                onClicked(selectedYear, selectedMonth)
            },
            isClick = true,
            clickBackgroundColor = Yellow,
            unClickBackgroundColor = Yellow
        )
    }
}

@Composable
fun DateNumberPicker(
    intRange: IntRange,
    value: Int,
    onCompleted: (Int) -> Unit
) {
    val pickerState = remember { mutableStateOf(value) }
    NumberPicker(
        modifier = Modifier.width(200.dp),
        value = pickerState.value,
        onValueChange = {
            pickerState.value = it
            onCompleted(it)
        },
        range = intRange
    )
}

@Preview(showBackground = true)
@Composable
private fun AccountBookDialogPreview() {
    AccountBookDialog(
        isShow = true,
        onDismissRequest = {},
        content = { YearAndMonthPicker(0, 0, onClicked = { year, month -> }) }
    )
}