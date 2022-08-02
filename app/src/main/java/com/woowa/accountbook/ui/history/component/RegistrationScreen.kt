package com.woowa.accountbook.ui.history.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowa.accountbook.common.NOW_DAY
import com.woowa.accountbook.ui.calendar.CalendarViewModel
import com.woowa.accountbook.ui.component.*
import com.woowa.accountbook.ui.history.HistoryViewModel
import com.woowa.accountbook.ui.iconpack.IconPack
import com.woowa.accountbook.ui.iconpack.LeftArrow
import com.woowa.accountbook.ui.theme.OffWhite
import com.woowa.accountbook.ui.theme.Purple
import com.woowa.accountbook.ui.theme.White
import com.woowa.accountbook.ui.theme.Yellow

@Composable
fun RegistrationScreen(
    historyViewModel: HistoryViewModel = hiltViewModel(),
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    navigationUp: () -> Unit = {}
) {
    val inComeIsChecked = rememberSaveable { mutableStateOf(true) }
    val expenseIsChecked = rememberSaveable { mutableStateOf(false) }
    val price = rememberSaveable { mutableStateOf("") }
    val payment = rememberSaveable { mutableStateOf("선택하세요") }
    val paymentId = rememberSaveable { mutableStateOf<Int?>(null) }
    val content = rememberSaveable { mutableStateOf("") }
    val category = rememberSaveable { mutableStateOf("선택하세요") }
    val categoryId = rememberSaveable { mutableStateOf<Int?>(null) }
    val (year, month) = calendarViewModel.yearMonthPair.value
    val paymentList = historyViewModel.payments.collectAsState().value
    val categoryList = historyViewModel.categories.collectAsState().value
    if (inComeIsChecked.value) historyViewModel.getCategories("1") else historyViewModel.getCategories(
        "0"
    )
    val selectedYear = rememberSaveable { mutableStateOf(year) }
    val selectedMonth = rememberSaveable { mutableStateOf(month) }
    val selectedDate = rememberSaveable { mutableStateOf(NOW_DAY) }

    Scaffold(
        backgroundColor = OffWhite,
        topBar = {
            AccountBookAppBar(
                title = "내역 등록",
                navigationIcon = IconPack.LeftArrow,
                onNavigationClicked = { navigationUp() }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            FilterButton(
                inComeIsChecked,
                expenseIsChecked,
                price,
                payment,
                content,
                category,
                selectedYear,
                year,
                selectedMonth,
                month,
                selectedDate
            )
            Spacer(modifier = Modifier.height(16.dp))

            val maxDate = remember {
                mutableStateOf(
                    calendarViewModel.getMaxDate(
                        selectedYear.value,
                        selectedMonth.value,
                        selectedDate.value
                    )
                )
            }
            InputText(
                label = "일자",
                content = {
                    InputDateText(
                        text = "${selectedYear.value}.${selectedMonth.value}.${selectedDate.value} ${
                            calendarViewModel.getDayOfWeek(
                                selectedYear.value,
                                selectedMonth.value,
                                selectedDate.value
                            )
                        }",
                        dialog = { isShow, onDismissRequest ->
                            AccountBookDialog(
                                isShow = isShow,
                                onDismissRequest = { onDismissRequest(it) },
                                modifier = Modifier.fillMaxWidth(),
                                content = {
                                    YearAndMonthAndDayPicker(
                                        selectedYear.value,
                                        selectedMonth.value,
                                        selectedDate.value,
                                        maxDate.value,
                                        onClicked = { year, month, day ->
                                            onDismissRequest(it)
                                            selectedYear.value = year
                                            selectedMonth.value = month
                                            selectedDate.value = day
                                        }
                                    )
                                }
                            )
                        }
                    )
                },
            )
            InputText(label = "금액") {
                InputNumberText(
                    price.value, onChanged = { price.value = it },
                )
            }
            InputText(label = if (inComeIsChecked.value) "입금수단" else "결제수단") {
                InputDropDownMenu(
                    title = payment.value,
                    paymentList = paymentList,
                    type = 0,
                    onSelected = { name, id ->
                        payment.value = name ?: ""
                        paymentId.value = id
                    }
                )
            }
            InputText(label = "분류") {
                InputDropDownMenu(
                    title = category.value,
                    categoryList = categoryList,
                    type = 1,
                    onSelected = { name, id ->
                        category.value = name ?: ""
                        categoryId.value = id
                    }
                )
            }
            InputText(label = "내용") {
                InputContentText(content.value, onChanged = { content.value = it })
            }
            saveButton(
                text = "등록하기",
                onClicked = {
                    historyViewModel.saveHistory(
                        money = price.value.toInt(),
                        categoryId = if (expenseIsChecked.value) {
                            if (categoryId.value == null) historyViewModel.getDefaultCategory() else categoryId.value
                        } else {
                            categoryId.value
                        },
                        content = content.value,
                        year = selectedYear.value,
                        month = selectedMonth.value,
                        day = selectedDate.value,
                        paymentId = paymentId.value!!
                    )
                    navigationUp()
                },
                price = price.value,
                paymentId.value,
                payment.value
            )
        }
    }
}

@Composable
private fun saveButton(
    text: String,
    onClicked: () -> Unit,
    price: String,
    paymentId: Int?,
    payment: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        TextButton(
            text = text,
            textColor = White,
            modifier = Modifier
                .width(328.dp)
                .height(50.dp),
            shape = RoundedCornerShape(14.dp),
            onClicked = { onClicked() },
            isClick = true,
            enabled = price != "" && payment != "선택하세요" && paymentId != null,
            enabledBackgroundColor = Yellow.copy(alpha = 0.5f),
            clickBackgroundColor = Yellow,
            unClickBackgroundColor = Yellow
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun FilterButton(
    inComeIsChecked: MutableState<Boolean>,
    expenseIsChecked: MutableState<Boolean>,
    price: MutableState<String>,
    payment: MutableState<String>,
    content: MutableState<String>,
    category: MutableState<String>,
    selectedYear: MutableState<Int>,
    year: Int,
    selectedMonth: MutableState<Int>,
    month: Int,
    selectedDate: MutableState<Int>
) {
    Row() {
        LeftCornerCheckButton(
            checkBox = false,
            checked = inComeIsChecked.value,
            onClicked = {
                inComeIsChecked.value = true
                expenseIsChecked.value = false
                price.value = ""
                payment.value = "선택하세요"
                content.value = ""
                category.value = "선택하세요"
                selectedYear.value = year
                selectedMonth.value = month
                selectedDate.value = NOW_DAY
            },
            onCheckedChange = { },
            disabledColor = White,
            checkedColor = White,
            uncheckedColor = White,
            checkmarkColor = Purple,
            labelText = "수입",
        )

        RightCornerCheckButton(
            checkBox = false,
            checked = expenseIsChecked.value,
            onClicked = {
                inComeIsChecked.value = false
                expenseIsChecked.value = true
                price.value = ""
                payment.value = "선택하세요"
                content.value = ""
                category.value = "선택하세요"
                selectedYear.value = year
                selectedMonth.value = month
                selectedDate.value = NOW_DAY
            },
            onCheckedChange = { },
            disabledColor = White,
            checkedColor = White,
            uncheckedColor = White,
            checkmarkColor = Purple,
            labelText = "지출",
        )
    }
}