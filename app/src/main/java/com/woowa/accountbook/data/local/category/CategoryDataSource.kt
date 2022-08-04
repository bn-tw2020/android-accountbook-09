package com.woowa.accountbook.data.local.category

import com.woowa.accountbook.data.entitiy.Category

interface CategoryDataSource {

    suspend fun findById(id: Int): Category?
    suspend fun findByNameAndIsIncome(name: String, isIncome: String): Category?
    suspend fun findByType(type: String): List<Category>
    suspend fun deleteById(list: List<Int>)
    suspend fun update(id: Int, name: String, isIncome: String, color: String)
    suspend fun save(name: String, color: String, isIncome: Boolean)
}