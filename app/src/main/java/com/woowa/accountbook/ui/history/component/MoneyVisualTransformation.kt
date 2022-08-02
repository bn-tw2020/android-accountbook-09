package com.woowa.accountbook.ui.history.component

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.woowa.accountbook.common.stringToMoneyFormat

class MoneyVisualTransformation() : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val price = if (text.text != "") stringToMoneyFormat(text.text.toInt()) else ""

        return TransformedText(
            AnnotatedString(price),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return price.length
                }

                override fun transformedToOriginal(offset: Int): Int = offset
            }
        )
    }
}