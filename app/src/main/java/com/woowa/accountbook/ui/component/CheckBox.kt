package com.woowa.accountbook.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowa.accountbook.ui.theme.Purple
import com.woowa.accountbook.ui.theme.Red
import com.woowa.accountbook.ui.theme.White

@Composable
fun AccountBookCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    checkedColor: Color,
    uncheckedColor: Color,
    checkmarkColor: Color
) {
    Checkbox(
        checked = checked,
        onCheckedChange = { onCheckedChange(it) },
        colors = CheckboxDefaults.colors(
            checkedColor = checkedColor,
            uncheckedColor = uncheckedColor,
            checkmarkColor = checkmarkColor
        ),
        modifier = Modifier.size(24.dp)
    )
}

@Preview(showBackground = false)
@Composable
fun CheckBoxPreview() {
    AccountBookCheckBox(
        checked = true,
        onCheckedChange = { },
        checkedColor = Red,
        uncheckedColor = Purple,
        checkmarkColor = White
    )
}

@Preview(showBackground = false)
@Composable
fun UnCheckBoxPreview() {
    AccountBookCheckBox(
        checked = false,
        onCheckedChange = { },
        checkedColor = Red,
        uncheckedColor = Purple,
        checkmarkColor = White
    )
}

@Preview(showBackground = false)
@Composable
fun HistoryCheckBoxPreview() {
    AccountBookCheckBox(
        checked = true,
        onCheckedChange = { },
        checkedColor = White,
        uncheckedColor = White,
        checkmarkColor = Purple
    )
}

@Preview(showBackground = false)
@Composable
fun HistoryUnCheckBoxPreview() {
    Row(
        modifier = Modifier.background(Purple)
    ) {
        AccountBookCheckBox(
            checked = false,
            onCheckedChange = { },
            checkedColor = White,
            uncheckedColor = White,
            checkmarkColor = Purple
        )
    }
}