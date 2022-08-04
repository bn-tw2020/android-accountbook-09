package com.woowa.accountbook.data.repository

import com.woowa.accountbook.data.entitiy.Category
import com.woowa.accountbook.data.local.category.CategoryDataSource
import com.woowa.accountbook.data.local.history.HistoryDataSource
import com.woowa.accountbook.domain.repository.category.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val historyDataSource: HistoryDataSource,
    private val categoryDataSource: CategoryDataSource
) : CategoryRepository {

    override suspend fun getCategoriesByType(type: String): Result<List<Category>> {
        return runCatching {
            categoryDataSource.findByType(type)
        }
    }

    override suspend fun removeCategories(list: List<Int>): Result<Boolean> {
        return runCatching {
            val result = list.asSequence()
                .map { id -> historyDataSource.existsByCategoryId(id) }
                .find { !it }
            if (result == false) {
                categoryDataSource.deleteById(list)
                true
            } else {
                false
            }
        }
    }

    override suspend fun updateCategory(id: Int, name: String, color: String, isIncome: Boolean) {
        runCatching {
            categoryDataSource.update(id, name, if (isIncome) "1" else "0", color)
        }
    }

    override suspend fun saveCategory(name: String, color: String, isIncome: Boolean) {
        runCatching {
            val category =
                categoryDataSource.findByNameAndIsIncome(name, if (isIncome) "1" else "0")
            if (category == null)
                categoryDataSource.save(name, color, isIncome)
        }
    }

}