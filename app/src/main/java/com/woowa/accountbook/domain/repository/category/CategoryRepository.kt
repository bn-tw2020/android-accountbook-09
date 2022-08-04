package com.woowa.accountbook.domain.repository.category

import com.woowa.accountbook.data.entitiy.Category

interface CategoryRepository {
    suspend fun getCategoriesByType(type: String): Result<List<Category>>
    suspend fun removeCategories(list: List<Int>): Result<Boolean>
    suspend fun updateCategory(id: Int, name: String, color: String, isIncome: Boolean)
    suspend fun saveCategory(name: String, color: String, isIncome: Boolean)
}