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
import com.woowa.accountbook.ui.theme.*

@Composable
fun HistoryItemTitle(
    textColor: Color,
    title: String,
    income: String? = null,
    expense: String? = null
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

        if (income != null && expense != null) {
            Row {
                Text(
                    text = "μμ",
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
                    text = "μ§μΆ",
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
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingItem(
    category: Category? = null,
    payment: Payment? = null,
    isEdit: Boolean,
    onClicked: (Int?) -> Unit = {},
    onLongClicked: (Boolean, Int?) -> Unit = { _, _ -> },
    onCheckedItem: (Boolean, Int?) -> Unit = { _, _ -> },
    content: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .combinedClickable(
                onClick = {
                    if (!isEdit) {
                        if (category != null) onClicked(category.id)
                        if (payment != null) onClicked(payment.id)
                    }
                },
                onLongClick = {
                    if (category != null) onLongClicked(isEdit, category.id)
                    if (payment != null) onLongClicked(isEdit, payment.id)
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEdit) {
            Spacer(modifier = Modifier.width(16.dp))
            AccountBookCheckBox(
                checked = if (Category.isDefault(category?.name) || Payment.isDefault(payment?.name)) false
                else category?.isChecked ?: (payment?.isChecked ?: false),
                enabled = if (category != null) !Category.isDefault(category.name!!) else !Payment.isDefault(
                    payment?.name!!
                ),
                onCheckedChange = {
                    if (category != null) onCheckedItem(it, category.id)
                    if (payment != null) onCheckedItem(it, payment.id)
                },
                checkedColor = Red,
                uncheckedColor = Purple,
                checkmarkColor = White
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column {
            content()
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryItem(
    history: History,
    isEdit: Boolean,
    onClicked: (Int, History) -> Unit,
    onLongClicked: (Boolean, Int) -> Unit,
    onCheckedItem: (Boolean, Int) -> Unit
) {

    Row(
        modifier = Modifier
            .combinedClickable(
                onClick = { if (!isEdit) onClicked(history.id, history) },
                onLongClick = { onLongClicked(isEdit, history.id) }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isEdit) {
            Spacer(modifier = Modifier.width(16.dp))
            AccountBookCheckBox(
                checked = history.isChecked,
                onCheckedChange = { onCheckedItem(it, history.id) },
                checkedColor = Red,
                uncheckedColor = Purple,
                checkmarkColor = White
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column {
            BothSideText(
                leftText = {
                    LabelText(
                        text = history.category?.name ?: "",
                        textStyle = MaterialTheme.typography.caption,
                        color = if (history.category?.color == null) OffWhite else Color(
                            android.graphics.Color.parseColor(
                                history.category.color
                            )
                        )
                    )
                },
                rightText = {
                    Text(
                        text = history.payment.name,
                        style = MaterialTheme.typography.caption,
                        color = Purple
                    )
                }
            )
            BothSideText(
                leftText = {
                    Text(
                        text = history.content,
                        style = MaterialTheme.typography.subtitle2,
                        color = Purple
                    )
                },
                rightText = {
                    Text(
                        text = "${rawToMoneyFormat(history.money, history.category?.isIncome)}μ",
                        style = MaterialTheme.typography.subtitle2,
                        color = if (history.category?.isIncome == 1) Green3 else Red
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryItemTitlePreview() {
    HistoryItemTitle(textColor = LightPurple, title = "7μ 15μΌ κΈ", income = "0", expense = "56,240")
}

@Preview(showBackground = true)
@Composable
private fun HistoryItemDefaultPreview() {
    HistoryItem(
        history =
        History(
            id = 1,
            money = 625321,
            content = "μ€νΈλ¦¬λ° μ κΈ° κ²°μ ",
            year = 2022,
            month = 7,
            day = 15,
            category = Category(
                id = 1,
                isIncome = 1,
                name = "κ΅ν΅",
                color = "#817DCE"
            ),
            payment = Payment(
                id = 1,
                name = "μΉ΄μΉ΄μ€λ±ν¬ μ²΄ν¬μΉ΄λ"
            )
        ),
        isEdit = false,
        onLongClicked = { _, _ -> },
        onCheckedItem = { _, _ -> },
        onClicked = {_, _ ->}
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
            content = "μ€νΈλ¦¬λ° μ κΈ° κ²°μ ",
            year = 2022,
            month = 7,
            day = 15,
            category = Category(
                id = 1,
                isIncome = 1,
                name = "μ©λ",
                color = "#817DCE"
            ),
            payment = Payment(
                id = 1,
                name = "μΉ΄μΉ΄μ€λ±ν¬ μ²΄ν¬μΉ΄λ"
            )
        ),
        isEdit = true,
        onLongClicked = { _, _ -> },
        onCheckedItem = { _, _ -> },
        onClicked = {_, _ ->}
    )
}