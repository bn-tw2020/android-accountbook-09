package com.woowa.accountbook.domain.repository.category

import com.woowa.accountbook.data.entitiy.Category

interface CategoryRepository {
    fun getCategoriesByType(type: String): Result<List<Category>>
    fun removeCategories(list: List<Int>): Result<Boolean>
    fun updateCategory(id: Int, name: String, color: String, isIncome: Boolean)
    fun saveCategory(name: String, color: String, isIncome: Boolean)
}