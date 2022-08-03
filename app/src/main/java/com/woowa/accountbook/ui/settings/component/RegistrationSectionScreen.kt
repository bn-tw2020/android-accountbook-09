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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    navigationUp: () -> Unit
) {
    val updateMode = -1
    val title = if (id == updateMode) {
        when (type) {
            "expense" -> "지출 카테고리 추가"
            "income" -> "수입 카테고리 추가"
            else -> "결제 수단 추가하기"
        }
    } else {
        when (type) {
            "expense" -> "지출 카테고리 수정"
            "income" -> "수입 카테고리 수정"
            else -> "결제 수단 수정하기"
        }
    }

    val name = rememberSaveable { mutableStateOf("") }
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

            val colorList = if (type == "expense") expenseColorList else incomeColorList
            val selectedItem = remember { mutableStateOf(colorList.first()) }
            if (type == "expense" || type == "income") {
                InputColor(colorList, selectedItem)
            }
            SaveButton(id, updateMode, type, settingViewModel, name, selectedItem, navigationUp)
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
    name: MutableState<String>,
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
                if (id == updateMode && (type == "expense" || type == "income")) {
                    settingViewModel.saveCategory(
                        name.value,
                        type != "expense",
                        selectedItem.value
                    )
                }
                if (id == updateMode && type == "payment") {
                    settingViewModel.savePayment(name.value)
                }
                navigationUp()
            },
            isClick = true,
            enabled = name.value != "" && !name.value.contains(" "),
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