package com.woowa.accountbook.ui.settings.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowa.accountbook.data.entitiy.Category
import com.woowa.accountbook.data.entitiy.Payment
import com.woowa.accountbook.ui.component.*
import com.woowa.accountbook.ui.iconpack.IconPack
import com.woowa.accountbook.ui.iconpack.LeftArrow
import com.woowa.accountbook.ui.iconpack.Plus
import com.woowa.accountbook.ui.iconpack.Trash
import com.woowa.accountbook.ui.settings.SettingViewModel
import com.woowa.accountbook.ui.theme.*

@Composable
fun SettingScreen(
    settingViewModel: SettingViewModel = hiltViewModel(),
    onSectionItemClicked: (Int?, String) -> Unit = { _, _ -> }
) {

    settingViewModel.getPayments()
    settingViewModel.getExpenseCategories()
    settingViewModel.getIncomeCategories()
    val paymentEditMode = remember { mutableStateOf(false) }
    val expenseEditMode = remember { mutableStateOf(false) }
    val incomeEditMode = remember { mutableStateOf(false) }
    val payments = settingViewModel.payments.collectAsState().value
    val expenseCategories = settingViewModel.expenseCategories.collectAsState().value
    val incomeCategories = settingViewModel.incomeCategories.collectAsState().value

    Scaffold(
        backgroundColor = OffWhite,
        topBar = {
            if (!paymentEditMode.value && !expenseEditMode.value && !incomeEditMode.value) {
                AccountBookAppBar(title = "설정")
            } else {

                AccountBookAppBar(
                    title = if (paymentEditMode.value)
                        "${payments.count { it.isChecked }}개 선택"
                    else if (expenseEditMode.value)
                        "${expenseCategories.count { it.isChecked }}개 선택"
                    else
                        "${incomeCategories.count { it.isChecked }}개 선택",
                    navigationIcon = IconPack.LeftArrow,
                    onNavigationClicked = {
                        if (paymentEditMode.value) {
                            paymentEditMode.value = !paymentEditMode.value
                            settingViewModel.resetCheckedItem("payment")
                        } else if (expenseEditMode.value) {
                            expenseEditMode.value = !expenseEditMode.value
                            settingViewModel.resetCheckedItem("expense")
                        } else {
                            incomeEditMode.value = !incomeEditMode.value
                            settingViewModel.resetCheckedItem("income")
                        }
                    },
                    actionIcon = IconPack.Trash,
                    actionIconColor = Red,
                    onActionClicked = {
                        if (paymentEditMode.value) {
                            paymentEditMode.value = !paymentEditMode.value
                            settingViewModel.removeItem("payment")
                        } else if (expenseEditMode.value) {
                            expenseEditMode.value = !expenseEditMode.value
                            settingViewModel.removeItem("expense")
                        } else {
                            incomeEditMode.value = !incomeEditMode.value
                            settingViewModel.removeItem("income")
                        }
                    }
                )
            }
        }
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(LightPurple)
        )

        LazyColumn {
            section(
                title = "결제수단",
                payments = payments,
                editMode = paymentEditMode.value,
                onClicked = { onSectionItemClicked(it, "payment") },
                onLongClicked = { mode, id ->
                    paymentEditMode.value = !mode
                    expenseEditMode.value = false
                    incomeEditMode.value = false
                    settingViewModel.resetCheckedItem("expense")
                    settingViewModel.resetCheckedItem("income")
                    settingViewModel.setCheckedItem(true, id, "payment")
                    if (!paymentEditMode.value) settingViewModel.resetCheckedItem("payment")
                },
                onCheckedItem = { isChecked, id ->
                    settingViewModel.setCheckedItem(isChecked, id, "payment")
                }
            )
            section(
                title = "지출 카테고리",
                category = expenseCategories,
                editMode = expenseEditMode.value,
                onClicked = { onSectionItemClicked(it, "expense") },
                onLongClicked = { mode, id ->
                    paymentEditMode.value = false
                    expenseEditMode.value = !mode
                    incomeEditMode.value = false
                    settingViewModel.resetCheckedItem("payment")
                    settingViewModel.resetCheckedItem("income")
                    settingViewModel.setCheckedItem(true, id, "expense")
                    if (!expenseEditMode.value) settingViewModel.resetCheckedItem("expense")
                },
                onCheckedItem = { isChecked, id ->
                    settingViewModel.setCheckedItem(isChecked, id, "expense")
                }
            )
            section(
                title = "수입 카테고리",
                category = incomeCategories,
                editMode = incomeEditMode.value,
                onClicked = { onSectionItemClicked(it, "income") },
                onLongClicked = { mode, id ->
                    paymentEditMode.value = false
                    expenseEditMode.value = false
                    incomeEditMode.value = !mode
                    settingViewModel.resetCheckedItem("payment")
                    settingViewModel.resetCheckedItem("expense")
                    settingViewModel.setCheckedItem(true, id, "income")
                    if (!incomeEditMode.value) settingViewModel.resetCheckedItem("income")
                },
                onCheckedItem = { isChecked, id ->
                    settingViewModel.setCheckedItem(isChecked, id, "income")
                }
            )
        }
    }
}

private fun LazyListScope.section(
    title: String,
    payments: List<Payment> = emptyList(),
    category: List<Category> = emptyList(),
    editMode: Boolean = false,
    onClicked: (Int?) -> Unit = {},
    onLongClicked: (Boolean, Int?) -> Unit = { _, _ -> },
    onCheckedItem: (Boolean, Int?) -> Unit = { _, _ -> },
) {
    item {
        HistoryItemTitle(textColor = LightPurple, title = title)
    }
    if (payments.isNotEmpty()) {
        itemsIndexed(payments) { idx, item ->
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Divider(color = Purple40)
                SettingItem(
                    payment = item,
                    isEdit = editMode,
                    onClicked = { onClicked(it) },
                    onLongClicked = { editMode, id -> onLongClicked(editMode, id) },
                    onCheckedItem = { isChecked, id -> onCheckedItem(isChecked, id) }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        text = item.name,
                        style = MaterialTheme.typography.subtitle2,
                        color = Purple
                    )
                }
            }
        }
    }
    if (category.isNotEmpty()) {
        itemsIndexed(category) { idx, item ->
            Column {
                Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Purple40)
                SettingItem(
                    category = item,
                    isEdit = editMode,
                    onClicked = { onClicked(it) },
                    onLongClicked = { editMode, id -> onLongClicked(editMode, id) },
                    onCheckedItem = { isChecked, id -> onCheckedItem(isChecked, id) }
                ) {
                    BothSideText(
                        leftText = {
                            Text(
                                text = item.name ?: "",
                                style = MaterialTheme.typography.subtitle2,
                                color = Purple
                            )
                        },
                        rightText = {
                            LabelText(
                                text = item.name ?: "",
                                textStyle = MaterialTheme.typography.caption,
                                color = if (item.color == null) OffWhite else Color(
                                    android.graphics.Color.parseColor(
                                        item.color
                                    )
                                )
                            )
                        }
                    )
                }
            }
        }
    }
    item {
        Divider(color = Purple40)
        AddItemText(onClicked = { onClicked(it) })
    }
    item {
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(LightPurple)
        )
    }
}

@Composable
fun AddItemText(
    onClicked: (Int?) -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "추가하기",
            color = Purple,
            style = MaterialTheme.typography.subtitle2
        )

        IconButton(onClick = { onClicked(-1) }) {
            Icon(imageVector = IconPack.Plus, contentDescription = "plus", tint = Purple)
        }
    }
}

@Preview(showBackground = false)
@Composable
fun SettingScreenPreview() {
    SettingScreen()
}