package com.woowa.accountbook.ui.history.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowa.accountbook.common.NOW_DAY
import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.ui.calendar.CalendarViewModel
import com.woowa.accountbook.ui.component.*
import com.woowa.accountbook.ui.history.HistoryViewModel
import com.woowa.accountbook.ui.iconpack.IconPack
import com.woowa.accountbook.ui.iconpack.LeftArrow
import com.woowa.accountbook.ui.theme.*

@Composable
fun RegistrationScreen(
    id: Int,
    historyViewModel: HistoryViewModel = hiltViewModel(),
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    navigationUp: () -> Unit = {},
    onSectionItemClicked: (Int?, String) -> Unit,
) {
    val updateMode = -1
    historyViewModel.getPayments()
    val history = historyViewModel.currentHistory
    var isIncome = history?.category?.isIncome == 1
    var isExpense = history?.category?.isIncome == 0
    if (id == updateMode) {
        isIncome = true
        isExpense = false
    }

    val inComeIsChecked = rememberSaveable { mutableStateOf(isIncome) }
    val expenseIsChecked = rememberSaveable { mutableStateOf(isExpense) }

    val price = rememberSaveable { mutableStateOf(history?.money?.toString() ?: "") }
    val payment = rememberSaveable { mutableStateOf(history?.payment?.name ?: "선택하세요") }
    val paymentId = rememberSaveable { mutableStateOf(history?.payment?.id) }
    val content = rememberSaveable { mutableStateOf(history?.content ?: "") }
    val category = rememberSaveable { mutableStateOf(history?.category?.name ?: "선택하세요") }
    val categoryId = rememberSaveable { mutableStateOf(history?.category?.id) }
    val (year, month) = calendarViewModel.yearMonthPair.value
    val paymentList = historyViewModel.payments.collectAsState().value
    val categoryList = historyViewModel.categories.collectAsState().value
    if (inComeIsChecked.value) historyViewModel.getCategories("1") else historyViewModel.getCategories(
        "0"
    )
    val selectedYear = rememberSaveable { mutableStateOf(history?.year ?: year) }
    val selectedMonth = rememberSaveable { mutableStateOf(history?.month ?: month) }
    val selectedDate = rememberSaveable { mutableStateOf(history?.day ?: NOW_DAY) }

    Scaffold(
        backgroundColor = OffWhite,
        topBar = {
            AccountBookAppBar(
                title = if (id == updateMode) "내역 등록" else "내역 수정",
                navigationIcon = IconPack.LeftArrow,
                onNavigationClicked = { navigationUp() }
            )
        }
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(LightPurple)
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            FilterButton(
                history,
                inComeIsChecked,
                expenseIsChecked,
                price,
                payment,
                paymentId,
                content,
                category,
                categoryId,
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
                    },
                    onAddItem = { onSectionItemClicked(it, "payment") }
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
                    },
                    onAddItem = {
                        onSectionItemClicked(
                            it,
                            if (inComeIsChecked.value) "income" else "expense"
                        )
                    }
                )
            }
            InputText(label = "내용") {
                InputContentText(content.value, onChanged = { content.value = it })
            }
            saveButton(
                text = if (id == updateMode) "등록하기" else "수정하기",
                onClicked = {
                    if (id == updateMode) {
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
                    } else {
                        historyViewModel.updateHistory(
                            id = id,
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
                    }
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
    history: History?,
    inComeIsChecked: MutableState<Boolean>,
    expenseIsChecked: MutableState<Boolean>,
    price: MutableState<String>,
    payment: MutableState<String>,
    paymentId: MutableState<Int?>,
    content: MutableState<String>,
    category: MutableState<String>,
    categoryId: MutableState<Int?>,
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
                price.value = history?.money?.toString() ?: ""
                payment.value = "선택하세요"
                content.value = history?.content ?: ""
                category.value = "선택하세요"
                categoryId.value = null
                paymentId.value = null
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
                price.value = history?.money?.toString() ?: ""
                payment.value = "선택하세요"
                content.value = history?.content ?: ""
                category.value = "선택하세요"
                categoryId.value = null
                paymentId.value = null
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
