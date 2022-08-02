package com.woowa.accountbook.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.woowa.accountbook.data.entitiy.Category
import com.woowa.accountbook.data.entitiy.Payment
import com.woowa.accountbook.ui.iconpack.Down
import com.woowa.accountbook.ui.iconpack.IconPack
import com.woowa.accountbook.ui.iconpack.Plus
import com.woowa.accountbook.ui.theme.Purple
import com.woowa.accountbook.ui.theme.White

@Composable
fun InputDropDownMenu(
    title: String,
    paymentList: List<Payment> = emptyList(),
    categoryList: List<Category> = emptyList(),
    type: Int,
    onSelected: (String?, Int?) -> Unit,
    onAddItem: () -> Unit = {}
) {
    val expanded = remember { mutableStateOf(false) }
    val rotateIcon = animateFloatAsState(
        targetValue = if (expanded.value) 180f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { expanded.value = !expanded.value }) {
        Text(
            text = title,
            color = Purple,
            style = MaterialTheme.typography.body2,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = IconPack.Down,
            contentDescription = "down_arrow",
            modifier = Modifier.graphicsLayer { rotationZ = rotateIcon.value },
            tint = Purple
        )
    }

    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            offset = DpOffset(0.dp, 17.dp),
            modifier = Modifier
                .width(260.dp)
                .height(150.dp)
                .background(White)
                .border(
                    width = 1.dp,
                    color = Purple,
                    shape = RoundedCornerShape(16.dp)
                ),
        ) {
            if (type == 0) {
                paymentList.forEach {
                    DropdownMenuItem(
                        onClick = {
                            onSelected(it.name, it.id)
                            expanded.value = false
                        }
                    ) {
                        Text(
                            text = it.name,
                            modifier = Modifier.weight(1f),
                            color = Purple,
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            } else {
                categoryList.forEach {
                    DropdownMenuItem(
                        onClick = {
                            onSelected(it.name, it.id)
                            expanded.value = false
                        }
                    ) {
                        Text(
                            text = it.name ?: "",
                            modifier = Modifier.weight(1f),
                            color = Purple,
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
            DropdownMenuItem(
                onClick = {}
            ) {
                Text(
                    text = "추가하기",
                    modifier = Modifier.weight(1f),
                    color = Purple,
                    style = MaterialTheme.typography.caption
                )
                IconButton(onClick = {}) {
                    Icon(
                        modifier = Modifier
                            .width(14.dp)
                            .height(14.dp),
                                imageVector = IconPack . Plus,
                        contentDescription = null,
                        tint = Purple
                    )
                }
            }
        }
    }
}