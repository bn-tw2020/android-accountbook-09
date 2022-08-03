package com.woowa.accountbook.data.local.category

import com.woowa.accountbook.data.entitiy.Category

interface CategoryDataSource {

    fun findById(id: Int): Category?
    fun findByNameAndIsIncome(name: String, isIncome: String): Category?
    fun findByType(type: String): List<Category>
    fun deleteById(list: List<Int>)
    fun save(name: String, color: String, isIncome: Boolean)
}