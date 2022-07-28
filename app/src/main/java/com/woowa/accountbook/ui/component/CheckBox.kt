package com.woowa.accountbook.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
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
    enabled: Boolean = true,
    disabledColor: Color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
    uncheckedColor: Color,
    checkmarkColor: Color
) {
    Checkbox(
        checked = checked,
        enabled = enabled,
        onCheckedChange = { onCheckedChange(it) },
        colors = CheckboxDefaults.colors(
            checkedColor = checkedColor,
            uncheckedColor = uncheckedColor,
            checkmarkColor = checkmarkColor,
            disabledColor = disabledColor
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

@Preview(showBackground = true)
@Composable
fun HistoryDisEnabledUnCheckBoxPreview() {
    Row {
        AccountBookCheckBox(
            checked = false,
            onCheckedChange = { },
            enabled = false,
            checkedColor = White,
            uncheckedColor = White,
            checkmarkColor = Purple
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryDisEnabledCheckBoxPreview() {
    Row {
        AccountBookCheckBox(
            checked = true,
            onCheckedChange = { },
            checkedColor = White,
            enabled = false,
            uncheckedColor = White,
            checkmarkColor = Purple
        )
    }
}