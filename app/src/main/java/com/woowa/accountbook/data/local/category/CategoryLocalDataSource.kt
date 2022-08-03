package com.woowa.accountbook.data.local.category

import android.content.ContentValues
import com.woowa.accountbook.data.entitiy.Category
import com.woowa.accountbook.data.local.DatabaseHelper
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(
    private val databaseHelper: DatabaseHelper
) : CategoryDataSource {

    override fun findById(id: Int): Category? {
        databaseHelper.readableDatabase.use { database ->
            val sql =
                "SELECT * FROM ${DatabaseHelper.TABLE_CATEGORY} WHERE ${DatabaseHelper.CATEGORY_COL_ID} = ?"
            val cursor = database.rawQuery(sql, arrayOf(id.toString()))
            return cursor.use {
                if (it.moveToNext()) {
                    Category(
                        id = it.getInt(0),
                        isIncome = it.getInt(1),
                        name = it.getString(2),
                        color = it.getString(3)
                    )
                } else null
            }
        }
    }

    override fun findByNameAndIsIncome(name: String, isIncome: String): Category? {
        databaseHelper.readableDatabase.use { database ->
            val sql =
                "SELECT * FROM ${DatabaseHelper.TABLE_CATEGORY} WHERE ${DatabaseHelper.CATEGORY_COL_NAME} = ? AND ${DatabaseHelper.CATEGORY_COL_IS_INCOME} = ?"
            val cursor = database.rawQuery(sql, arrayOf(name, isIncome))
            return cursor.use {
                if (it.moveToNext()) {
                    Category(
                        id = it.getInt(0),
                        isIncome = it.getInt(1),
                        name = it.getString(2),
                        color = it.getString(3)
                    )
                } else null
            }
        }
    }

    override fun findByType(type: String): List<Category> {
        val categoryList = mutableListOf<Category>()
        databaseHelper.readableDatabase.use { database ->
            val sql =
                "SELECT * FROM ${DatabaseHelper.TABLE_CATEGORY} WHERE ${DatabaseHelper.CATEGORY_COL_IS_INCOME} = ?"
            val cursor = database.rawQuery(sql, arrayOf(type))
            return cursor.use {
                while (it.moveToNext()) {
                    val id = it.getInt(0)
                    val isIncome = it.getInt(1)
                    val name = it.getString(2)
                    val color = it.getString(3)
                    categoryList.add(
                        Category(
                            id = id,
                            isIncome = isIncome,
                            name = name,
                            color = color
                        )
                    )
                }
                categoryList
            }
        }
    }

    override fun deleteById(list: List<Int>) {
        databaseHelper.writableDatabase.use { database ->
            list.forEach { id ->
                database.execSQL("DELETE FROM ${DatabaseHelper.TABLE_CATEGORY} WHERE ${DatabaseHelper.CATEGORY_COL_ID} = $id")
            }
        }
    }

    override fun update(id: Int, name: String, isIncome: String, color: String) {
        databaseHelper.writableDatabase.use { database ->
            val contentValues = ContentValues().apply {
                put(DatabaseHelper.CATEGORY_COL_NAME, name)
                put(DatabaseHelper.CATEGORY_COL_IS_INCOME, isIncome)
                put(DatabaseHelper.CATEGORY_COL_COLOR, color)
            }
            database.update(DatabaseHelper.TABLE_CATEGORY, contentValues, "_id=$id", null)
        }
    }

    override fun save(name: String, color: String, isIncome: Boolean) {
        databaseHelper.writableDatabase.use { database ->
            val contentValues = ContentValues().apply {
                put(DatabaseHelper.CATEGORY_COL_NAME, name)
                put(DatabaseHelper.CATEGORY_COL_COLOR, color)
                put(DatabaseHelper.CATEGORY_COL_IS_INCOME, isIncome)
            }
            database.insert(DatabaseHelper.TABLE_CATEGORY, null, contentValues)
        }
    }
}