package com.woowa.accountbook.ui.settings.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woowa.accountbook.ui.component.*
import com.woowa.accountbook.ui.iconpack.IconPack
import com.woowa.accountbook.ui.iconpack.LeftArrow
import com.woowa.accountbook.ui.settings.SettingViewModel
import com.woowa.accountbook.ui.theme.*

@Composable
fun RegistrationSectionScreen(
    settingViewModel: SettingViewModel = hiltViewModel(),
    id: Int,
    type: String?,
    navigationUp: () -> Unit,
) {
    val addMode = -1
    val colorList = if (type == "expense") expenseColorList else incomeColorList
    val name = rememberSaveable { mutableStateOf("") }
    val selectedItem = remember { mutableStateOf(colorList.first()) }

    val title = if (id == addMode) {
        when (type) {
            "expense" -> "지출 카테고리 추가"
            "income" -> "수입 카테고리 추가"
            else -> "결제 수단 추가하기"
        }
    } else {
        when (type) {
            "expense" -> {
                val expenseCategories = settingViewModel.expenseCategories.collectAsState().value
                val expenseCategory = expenseCategories.find { it.id == id }
                name.value = expenseCategory?.name ?: ""
                selectedItem.value =
                    Color(android.graphics.Color.parseColor(expenseCategory?.color))
                "지출 카테고리 수정"
            }
            "income" -> {
                val incomeCategories = settingViewModel.incomeCategories.collectAsState().value
                val incomeCategory = incomeCategories.find { it.id == id }
                name.value = incomeCategory?.name ?: ""
                selectedItem.value = Color(android.graphics.Color.parseColor(incomeCategory?.color))
                "수입 카테고리 수정"
            }
            else -> {
                val payments = settingViewModel.payments.collectAsState().value
                name.value = payments.find { it.id == id }?.name ?: ""
                "결제 수단 수정하기"
            }
        }
    }

    Scaffold(
        backgroundColor = OffWhite,
        topBar = {
            AccountBookAppBar(
                title = title,
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
        Column {
            InputText(label = "이름") {
                InputContentText(name.value, onChanged = { name.value = it })
            }
            if (type == "expense" || type == "income") {
                InputColor(colorList, selectedItem)
            }
            SaveButton(
                id,
                addMode,
                type,
                settingViewModel,
                name.value,
                selectedItem,
                navigationUp
            )
        }
    }
}

@Composable
private fun InputColor(
    colorList: List<Color>,
    selectedItem: MutableState<Color>
) {
    HistoryItemTitle(textColor = LightPurple, title = "색상")
    Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Purple40)
    ColorPalette(
        colorList = colorList,
        selectedItem = selectedItem.value,
        onClicked = { selectedItem.value = it }
    )
    Spacer(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(LightPurple)
    )
}

@Composable
private fun SaveButton(
    id: Int,
    updateMode: Int,
    type: String?,
    settingViewModel: SettingViewModel,
    name: String,
    selectedItem: MutableState<Color>,
    navigationUp: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        TextButton(
            text = if (id == updateMode) "등록하기" else "수정하기",
            textColor = White,
            modifier = Modifier
                .width(328.dp)
                .height(50.dp),
            shape = RoundedCornerShape(14.dp),
            onClicked = {
                if (id == updateMode) { // 등록
                    if (type == "expense" || type == "income")
                        settingViewModel.saveCategory(name, type != "expense", selectedItem.value)
                    else
                        settingViewModel.savePayment(name)
                } else {
                    if (type == "expense" || type == "income")
                        settingViewModel.updateCategory(
                            id,
                            name,
                            selectedItem.value,
                            type != "expense"
                        )
                    else
                        settingViewModel.updatePayment(id, name)
                }
                navigationUp()
            },
            isClick = true,
            enabled = name != "" && !name.contains(" "),
            enabledBackgroundColor = Yellow.copy(alpha = 0.5f),
            clickBackgroundColor = Yellow,
            unClickBackgroundColor = Yellow
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColorPalette(colorList: List<Color>, selectedItem: Color, onClicked: (Color) -> Unit) {

    LazyVerticalGrid(
        modifier = Modifier.selectableGroup(),
        cells = GridCells.Fixed(count = 10),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(colorList) { color ->
            Box(
                modifier = Modifier
                    .background(color)
                    .width(24.dp)
                    .height(24.dp)
                    .border(
                        width = if (selectedItem == color) 0.dp else 4.dp,
                        color = OffWhite
                    )
                    .selectable(
                        selected = selectedItem == color,
                        enabled = true,
                        role = Role.RadioButton
                    ) {
                        onClicked(color)
                    }
            )
        }
    }
}