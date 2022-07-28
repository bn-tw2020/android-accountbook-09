package com.woowa.accountbook.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowa.accountbook.common.rawToMoneyFormat
import com.woowa.accountbook.data.entitiy.Category
import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.data.entitiy.Payment
import com.woowa.accountbook.ui.theme.LightPurple
import com.woowa.accountbook.ui.theme.Purple
import com.woowa.accountbook.ui.theme.Red
import com.woowa.accountbook.ui.theme.White

@Composable
fun HistoryItemTitle(
    textColor: Color,
    title: String,
    income: String,
    expense: String
) {
    Spacer(modifier = Modifier.height(24.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = textColor
        )

        Row {
            Text(
                text = "수입",
                style = MaterialTheme.typography.caption,
                color = textColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = income,
                style = MaterialTheme.typography.caption,
                color = textColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "지출",
                style = MaterialTheme.typography.caption,
                color = textColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = expense,
                style = MaterialTheme.typography.caption,
                color = textColor
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryItem(
    history: History,
    isEdit: Boolean,
    onLongClicked: (Boolean, Int) -> Unit,
    onCheckedItem: (Boolean, Int) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .combinedClickable(
                onClick = {},
                onLongClick = { onLongClicked(isEdit, history.id) }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEdit) {
            AccountBookCheckBox(
                checked = history.isChecked,
                onCheckedChange = { onCheckedItem(it, history.id) },
                checkedColor = Red,
                uncheckedColor = Purple,
                checkmarkColor = White
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LabelText(
                    text = history.category.name,
                    textStyle = MaterialTheme.typography.caption,
                    color = Color(android.graphics.Color.parseColor(history.category.color))
                )
                Text(
                    text = history.payment.name,
                    style = MaterialTheme.typography.caption,
                    color = Purple
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = history.content,
                    style = MaterialTheme.typography.subtitle2,
                    color = Purple
                )
                Text(
                    text = "${rawToMoneyFormat(history.money, history.category.isIncome)}원",
                    style = MaterialTheme.typography.subtitle2,
                    color = Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryItemTitlePreview() {
    HistoryItemTitle(textColor = LightPurple, title = "7월 15일 금", income = "0", expense = "56,240")
}

@Preview(showBackground = true)
@Composable
private fun HistoryItemDefaultPreview() {
    HistoryItem(
        history =
        History(
            id = 1,
            money = 625321,
            content = "스트리밍 정기 결제",
            year = 2022,
            month = 7,
            day = 15,
            category = Category(
                id = 1,
                isIncome = 1,
                name = "여가/문화",
                color = "#817DCE"
            ),
            payment = Payment(
                id = 1,
                name = "카카오뱅크 체크카드"
            )
        ),
        isEdit = false,
        onLongClicked = { _, _ -> },
        onCheckedItem = { _, _ -> }
    )
}

@Preview(showBackground = true)
@Composable
private fun HistoryItemEditCheckedPreview() {
    HistoryItem(
        history =
        History(
            id = 1,
            money = 625321,
            content = "스트리밍 정기 결제",
            year = 2022,
            month = 7,
            day = 15,
            category = Category(
                id = 1,
                isIncome = 1,
                name = "여가/문화",
                color = "#817DCE"
            ),
            payment = Payment(
                id = 1,
                name = "카카오뱅크 체크카드"
            )
        ),
        isEdit = true,
        onLongClicked = { _, _ -> },
        onCheckedItem = { _, _ -> }
    )
}