package com.woowa.accountbook.data.repository

import com.woowa.accountbook.data.entitiy.Category
import com.woowa.accountbook.data.local.category.CategoryDataSource
import com.woowa.accountbook.domain.repository.category.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDataSource: CategoryDataSource
) : CategoryRepository {

    override fun getCategoriesByType(type: String): Result<List<Category>> {
        return runCatching { categoryDataSource.findByType(type) }
    }

    override fun removeCategories(list: List<Int>) {
        TODO("Not yet implemented")
    }

    override fun saveCategory(name: String, color: String, isIncome: Boolean) {
        runCatching {
            val category =
                categoryDataSource.findByNameAndIsIncome(name, if (isIncome) "1" else "0")
            if (category == null)
                categoryDataSource.save(name, color, isIncome)
        }
    }

}