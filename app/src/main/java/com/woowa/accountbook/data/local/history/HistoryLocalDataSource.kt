package com.woowa.accountbook.data.local.history

import android.content.ContentValues
import com.woowa.accountbook.data.entitiy.Category
import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.data.entitiy.Payment
import com.woowa.accountbook.data.local.DatabaseHelper
import javax.inject.Inject

class HistoryLocalDataSource @Inject constructor(
    private val databaseHelper: DatabaseHelper
) : HistoryDataSource {

    override suspend fun findById(id: Int): History? {
        databaseHelper.readableDatabase.let { database ->
            val sql =
                "SELECT * FROM ${DatabaseHelper.TABLE_ACCOUNT_BOOK} as T, ${DatabaseHelper.TABLE_CATEGORY}, ${DatabaseHelper.TABLE_PAYMENT} WHERE T.${DatabaseHelper.ACCOUNT_BOOK_COL_CATEGORY} = ${DatabaseHelper.TABLE_CATEGORY}.${DatabaseHelper.CATEGORY_COL_ID} AND T.${DatabaseHelper.ACCOUNT_BOOK_COL_PAYMENT} = ${DatabaseHelper.TABLE_PAYMENT}.${DatabaseHelper.PAYMENT_COL_ID} AND T.${DatabaseHelper.ACCOUNT_BOOK_COL_ID} = ?"
            val cursor = database.rawQuery(sql, arrayOf(id.toString()))
            return cursor.use {
                if (it.moveToNext()) {
                    val id = it.getInt(0)
                    val money = it.getInt(1)
                    val content = it.getString(4)
                    val year = it.getInt(5)
                    val month = it.getInt(6)
                    val day = it.getInt(7)
                    val categoryIdPK = it.getInt(8)
                    val isIncome = it.getInt(9)
                    val categoryName = it.getString(10)
                    val color = it.getString(11)
                    val paymentIdPK = it.getInt(12)
                    val paymentName = it.getString(13)

                    History(
                        id = id,
                        money = money,
                        content = content,
                        year = year,
                        month = month,
                        day = day,
                        Category(
                            id = categoryIdPK,
                            isIncome = if (categoryIdPK == 0) 1 else isIncome,
                            name = categoryName,
                            color = color
                        ),
                        Payment(
                            id = paymentIdPK,
                            name = paymentName
                        )
                    )
                } else null
            }
        }
    }

    override fun existsByCategoryId(id: Int): Boolean {
        databaseHelper.readableDatabase.use { database ->
            val sql =
                "SELECT EXISTS (SELECT * FROM ${DatabaseHelper.TABLE_ACCOUNT_BOOK} WHERE ${DatabaseHelper.ACCOUNT_BOOK_COL_CATEGORY} = ?) as exist"
            val cursor = database.rawQuery(sql, arrayOf(id.toString()))
            return cursor.use {
                if (it.moveToNext()) {
                    it.getInt(0) == 1
                } else false
            }
        }
    }

    override fun existsByPaymentId(id: Int): Boolean {
        databaseHelper.readableDatabase.let { database ->
            val sql =
                "SELECT EXISTS (SELECT * FROM ${DatabaseHelper.TABLE_ACCOUNT_BOOK} WHERE ${DatabaseHelper.ACCOUNT_BOOK_COL_PAYMENT} = ?) as exist"
            val cursor = database.rawQuery(sql, arrayOf(id.toString()))
            return cursor.use {
                if (it.moveToNext()) {
                    it.getInt(0) == 1
                } else false
            }
        }
    }

    override suspend fun findByAll(year: Int): List<History> {
        val historyList = mutableListOf<History>()
        databaseHelper.readableDatabase.let { database ->
            val sql =
                "SELECT * FROM (SELECT * FROM ${DatabaseHelper.TABLE_ACCOUNT_BOOK} WHERE ${DatabaseHelper.TABLE_ACCOUNT_BOOK}.${DatabaseHelper.ACCOUNT_BOOK_COL_YEAR} = ? ORDER BY ${DatabaseHelper.ACCOUNT_BOOK_COL_MONTH} DESC) as T LEFT JOIN ${DatabaseHelper.TABLE_CATEGORY} ON T.${DatabaseHelper.ACCOUNT_BOOK_COL_CATEGORY} = ${DatabaseHelper.TABLE_CATEGORY}.${DatabaseHelper.CATEGORY_COL_ID} LEFT JOIN ${DatabaseHelper.TABLE_PAYMENT} ON T.${DatabaseHelper.ACCOUNT_BOOK_COL_PAYMENT} = ${DatabaseHelper.TABLE_PAYMENT}.${DatabaseHelper.PAYMENT_COL_ID}"
            val cursor = database.rawQuery(sql, arrayOf(year.toString()))
            return cursor.use {
                while (it.moveToNext()) {
                    val id = it.getInt(0)
                    val money = it.getInt(1)
                    val content = it.getString(4)
                    val year = it.getInt(5)
                    val month = it.getInt(6)
                    val day = it.getInt(7)
                    val categoryIdPK = it.getInt(8)
                    val isIncome = it.getInt(9)
                    val categoryName = it.getString(10)
                    val color = it.getString(11)
                    val paymentIdPK = it.getInt(12)
                    val paymentName = it.getString(13)

                    val history = History(
                        id = id,
                        money = money,
                        content = content,
                        year = year,
                        month = month,
                        day = day,
                        Category(
                            id = categoryIdPK,
                            isIncome = if (categoryIdPK == 0) 1 else isIncome,
                            name = categoryName,
                            color = color
                        ),
                        Payment(
                            id = paymentIdPK,
                            name = paymentName
                        )
                    )
                    historyList.add(history)
                }
                historyList
            }
        }
    }

    override suspend fun findByMonth(month: String): List<History> {
        val historyList = mutableListOf<History>()
        databaseHelper.readableDatabase.let { database ->
            val sql =
                "SELECT * FROM (SELECT * FROM ${DatabaseHelper.TABLE_ACCOUNT_BOOK} WHERE ${DatabaseHelper.ACCOUNT_BOOK_COL_MONTH} = $month ORDER BY ${DatabaseHelper.ACCOUNT_BOOK_COL_DAY} DESC) as T LEFT JOIN ${DatabaseHelper.TABLE_CATEGORY} ON T.${DatabaseHelper.ACCOUNT_BOOK_COL_CATEGORY} = ${DatabaseHelper.TABLE_CATEGORY}.${DatabaseHelper.CATEGORY_COL_ID} LEFT JOIN ${DatabaseHelper.TABLE_PAYMENT} ON T.${DatabaseHelper.ACCOUNT_BOOK_COL_PAYMENT} = ${DatabaseHelper.TABLE_PAYMENT}.${DatabaseHelper.PAYMENT_COL_ID}"
            val cursor = database.rawQuery(sql, null)
            return cursor.use {
                while (it.moveToNext()) {
                    val id = it.getInt(0)
                    val money = it.getInt(1)
                    val content = it.getString(4)
                    val year = it.getInt(5)
                    val month = it.getInt(6)
                    val day = it.getInt(7)
                    val categoryIdPK = it.getInt(8)
                    val isIncome = it.getInt(9)
                    val categoryName = it.getString(10)
                    val color = it.getString(11)
                    val paymentIdPK = it.getInt(12)
                    val paymentName = it.getString(13)
                    val history = History(
                        id = id,
                        money = money,
                        content = content,
                        year = year,
                        month = month,
                        day = day,
                        Category(
                            id = categoryIdPK,
                            isIncome = if (categoryIdPK == 0) 1 else isIncome,
                            name = categoryName,
                            color = color
                        ),
                        Payment(
                            id = paymentIdPK,
                            name = paymentName
                        )
                    )
                    historyList.add(history)
                }
                historyList
            }
        }
    }

    override suspend fun findByMonthAndType(month: String, type: Boolean): List<History> {
        val historyList = mutableListOf<History>()
        databaseHelper.readableDatabase.let { database ->
            val isIncome = if (type) "1" else "0"
            val sql =
                "SELECT * FROM (SELECT * FROM ${DatabaseHelper.TABLE_ACCOUNT_BOOK} WHERE ${DatabaseHelper.ACCOUNT_BOOK_COL_MONTH} = ? ORDER BY ${DatabaseHelper.ACCOUNT_BOOK_COL_MONTH} DESC) as T, ${DatabaseHelper.TABLE_CATEGORY}, ${DatabaseHelper.TABLE_PAYMENT} WHERE T.${DatabaseHelper.ACCOUNT_BOOK_COL_CATEGORY} = ${DatabaseHelper.TABLE_CATEGORY}.${DatabaseHelper.CATEGORY_COL_ID} AND T.${DatabaseHelper.ACCOUNT_BOOK_COL_PAYMENT} = ${DatabaseHelper.TABLE_PAYMENT}.${DatabaseHelper.PAYMENT_COL_ID} AND ${DatabaseHelper.TABLE_CATEGORY}.${DatabaseHelper.CATEGORY_COL_IS_INCOME} = ?"
            val cursor = database.rawQuery(sql, arrayOf(month, isIncome))
            return cursor.use {
                while (it.moveToNext()) {
                    val id = it.getInt(0)
                    val money = it.getInt(1)
                    val content = it.getString(4)
                    val year = it.getInt(5)
                    val month = it.getInt(6)
                    val day = it.getInt(7)
                    val categoryIdPK = it.getInt(8)
                    val isIncome = it.getInt(9)
                    val categoryName = it.getString(10)
                    val color = it.getString(11)
                    val paymentIdPK = it.getInt(12)
                    val paymentName = it.getString(13)

                    val history = History(
                        id = id,
                        money = money,
                        content = content,
                        year = year,
                        month = month,
                        day = day,
                        Category(
                            id = categoryIdPK,
                            isIncome = if (categoryIdPK == 0) 1 else isIncome,
                            name = categoryName,
                            color = color
                        ),
                        Payment(
                            id = paymentIdPK,
                            name = paymentName
                        )
                    )
                    historyList.add(history)
                }
                historyList
            }
        }
    }

    override suspend fun deleteById(list: List<Int>) {
        databaseHelper.writableDatabase.let { database ->
            list.forEach { id ->
                database.execSQL("DELETE FROM ${DatabaseHelper.TABLE_ACCOUNT_BOOK} WHERE ${DatabaseHelper.ACCOUNT_BOOK_COL_ID} = $id")
            }
        }
    }

    override suspend fun update(
        id: Int,
        money: Int,
        categoryId: Int?,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    ) {
        databaseHelper.writableDatabase.let { database ->
            val contentValues = ContentValues().apply {
                put(DatabaseHelper.ACCOUNT_BOOK_COL_MONEY, money)
                put(DatabaseHelper.ACCOUNT_BOOK_COL_CATEGORY, categoryId)
                put(DatabaseHelper.ACCOUNT_BOOK_COL_CONTENT, content)
                put(DatabaseHelper.ACCOUNT_BOOK_COL_YEAR, year)
                put(DatabaseHelper.ACCOUNT_BOOK_COL_MONTH, month)
                put(DatabaseHelper.ACCOUNT_BOOK_COL_DAY, day)
                put(DatabaseHelper.ACCOUNT_BOOK_COL_PAYMENT, paymentId)
            }
            database.update(DatabaseHelper.TABLE_ACCOUNT_BOOK, contentValues, "_id=$id", null)
        }
    }

    override suspend fun save(
        money: Int,
        categoryId: Int?,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    ) {
        databaseHelper.writableDatabase.let { database ->
            val contentValues = ContentValues().apply {
                put(DatabaseHelper.ACCOUNT_BOOK_COL_MONEY, money)
                put(DatabaseHelper.ACCOUNT_BOOK_COL_CATEGORY, categoryId)
                put(DatabaseHelper.ACCOUNT_BOOK_COL_CONTENT, content)
                put(DatabaseHelper.ACCOUNT_BOOK_COL_YEAR, year)
                put(DatabaseHelper.ACCOUNT_BOOK_COL_MONTH, month)
                put(DatabaseHelper.ACCOUNT_BOOK_COL_DAY, day)
                put(DatabaseHelper.ACCOUNT_BOOK_COL_PAYMENT, paymentId)
            }
            database.insert(DatabaseHelper.TABLE_ACCOUNT_BOOK, null, contentValues)
        }
    }
}