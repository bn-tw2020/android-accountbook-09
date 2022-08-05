package com.woowa.accountbook.data.entitiy

data class Payment(
    val id: Int,
    val name: String,
    var isChecked: Boolean = false
) {

    companion object {
        fun isDefault(name: String?): Boolean {
            name ?: return false
            val paymentDefault = listOf("현대카드", "카카오뱅크 체크카드")
            return paymentDefault.contains(name)
        }
    }
}