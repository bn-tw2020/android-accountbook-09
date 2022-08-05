package com.woowa.accountbook.data.entitiy

data class Category(
    val id: Int?,
    val isIncome: Int?,
    val name: String?,
    val color: String?,
    var isChecked: Boolean = false
) {
    companion object {
        fun isDefault(name: String?): Boolean {
            name ?: return false
            val expenseDefault = listOf("교통", "문화/여가", "미분류", "생활", "쇼핑/뷰티", "식비", "의료/건강")
            val incomeDefault = listOf("월급", "용돈", "기타")
            return expenseDefault.contains(name) || incomeDefault.contains(name)
        }
    }
}