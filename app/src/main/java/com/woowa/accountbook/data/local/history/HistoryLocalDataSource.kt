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

    override fun findByAll(): List<History> {
        val historyList = mutableListOf<History>()
        databaseHelper.readableDatabase.use { database ->
            val sql =
                "SELECT * FROM (SELECT * FROM ${DatabaseHelper.TABLE_ACCOUNT_BOOK} ORDER BY ${DatabaseHelper.ACCOUNT_BOOK_COL_MONTH} DESC) as T, ${DatabaseHelper.TABLE_CATEGORY}, ${DatabaseHelper.TABLE_PAYMENT} WHERE T.${DatabaseHelper.ACCOUNT_BOOK_COL_CATEGORY} = ${DatabaseHelper.TABLE_CATEGORY}.${DatabaseHelper.CATEGORY_COL_ID} AND T.${DatabaseHelper.ACCOUNT_BOOK_COL_PAYMENT} = ${DatabaseHelper.TABLE_PAYMENT}.${DatabaseHelper.PAYMENT_COL_ID}"
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
                            isIncome = isIncome,
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

    override fun findByMonth(month: String): List<History> {
        val historyList = mutableListOf<History>()
        databaseHelper.readableDatabase.use { database ->
            val sql =
                "SELECT * FROM (SELECT * FROM ${DatabaseHelper.TABLE_ACCOUNT_BOOK} WHERE ${DatabaseHelper.ACCOUNT_BOOK_COL_MONTH} = $month ORDER BY ${DatabaseHelper.ACCOUNT_BOOK_COL_DAY} DESC) as T, ${DatabaseHelper.TABLE_CATEGORY}, ${DatabaseHelper.TABLE_PAYMENT} WHERE T.${DatabaseHelper.ACCOUNT_BOOK_COL_CATEGORY} = ${DatabaseHelper.TABLE_CATEGORY}.${DatabaseHelper.CATEGORY_COL_ID} AND T.${DatabaseHelper.ACCOUNT_BOOK_COL_PAYMENT} = ${DatabaseHelper.TABLE_PAYMENT}.${DatabaseHelper.PAYMENT_COL_ID}"
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
                            isIncome = isIncome,
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

    override fun findByMonthAndType(month: String, type: Boolean): List<History> {
        val historyList = mutableListOf<History>()
        databaseHelper.readableDatabase.use { database ->
            val isIncome = if (type) "1" else "0"
            val sql =
                "SELECT * FROM (SELECT * FROM ${DatabaseHelper.TABLE_ACCOUNT_BOOK} WHERE ${DatabaseHelper.ACCOUNT_BOOK_COL_MONTH} = $month ORDER BY ${DatabaseHelper.ACCOUNT_BOOK_COL_MONTH} DESC) as T, ${DatabaseHelper.TABLE_CATEGORY}, ${DatabaseHelper.TABLE_PAYMENT} WHERE T.${DatabaseHelper.ACCOUNT_BOOK_COL_CATEGORY} = ${DatabaseHelper.TABLE_CATEGORY}.${DatabaseHelper.CATEGORY_COL_ID} AND T.${DatabaseHelper.ACCOUNT_BOOK_COL_PAYMENT} = ${DatabaseHelper.TABLE_PAYMENT}.${DatabaseHelper.PAYMENT_COL_ID} AND ${DatabaseHelper.TABLE_CATEGORY}.${DatabaseHelper.CATEGORY_COL_IS_INCOME} = $isIncome"
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
                            isIncome = isIncome,
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

    override fun deleteById(list: List<Int>) {
        databaseHelper.writableDatabase.use { database ->
            list.forEach { id ->
                database.execSQL("DELETE FROM ${DatabaseHelper.TABLE_ACCOUNT_BOOK} WHERE ${DatabaseHelper.ACCOUNT_BOOK_COL_ID} = $id")
            }
        }
    }

    override fun save(
        money: Int,
        categoryId: Int,
        content: String,
        year: Int,
        month: Int,
        day: Int,
        paymentId: Int
    ) {
        databaseHelper.writableDatabase.use { database ->
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