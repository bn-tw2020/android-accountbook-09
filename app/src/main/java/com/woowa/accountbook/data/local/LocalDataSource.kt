package com.woowa.accountbook.data.local

import android.content.ContentValues
import com.woowa.accountbook.data.entitiy.Category
import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.data.entitiy.Payment

class LocalDataSource(
    private val databaseHelper: DatabaseHelper
): DataSource {

    /**
     * @param type: 지출(0), 수입(1)
     * @description 지출, 수입에 따른 카테고리 정보 가져오기
     */
    override fun findByCategoryType(type: String): List<Category> {
        val categoryList = mutableListOf<Category>()
        databaseHelper.readableDatabase.use { database ->
            val sql = "SELECT * FROM ${DatabaseHelper.TABLE_CATEGORY} WHERE ${DatabaseHelper.CATEGORY_COL_IS_INCOME} = ?"
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

    override fun findAllPayment(): List<Payment> {
        val paymentList = mutableListOf<Payment>()
        databaseHelper.readableDatabase.use { database ->
            val cursor = database.query(
                DatabaseHelper.TABLE_PAYMENT,
                arrayOf(DatabaseHelper.PAYMENT_COL_ID, DatabaseHelper.PAYMENT_COL_NAME),
                null,
                null,
                null,
                null,
                null,
                null
            )
            return cursor.use {
                while (it.moveToNext()) {
                    val id = it.getInt(0)
                    val name = it.getString(1)
                    paymentList.add(
                        Payment(
                            id = id,
                            name = name
                        )
                    )
                }
                paymentList
            }
        }
    }

    /**
     * @param month: 조회 하고 싶은 월
     */
    override fun findByHistoryMonth(month: String): List<History> {
        val historyList = mutableListOf<History>()
        databaseHelper.readableDatabase.use { database ->

            val sql =
                "SELECT * FROM (SELECT * FROM ${DatabaseHelper.TABLE_ACCOUNT_BOOK} ORDER BY ${DatabaseHelper.ACCOUNT_BOOK_COL_DAY} DESC) as T, ${DatabaseHelper.TABLE_CATEGORY}, ${DatabaseHelper.TABLE_PAYMENT} WHERE T.${DatabaseHelper.ACCOUNT_BOOK_COL_CATEGORY} = ${DatabaseHelper.TABLE_CATEGORY}.${DatabaseHelper.CATEGORY_COL_ID} AND T.${DatabaseHelper.ACCOUNT_BOOK_COL_PAYMENT} = ${DatabaseHelper.TABLE_PAYMENT}.${DatabaseHelper.PAYMENT_COL_ID}"
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

    override fun deleteByHistoryId(list: List<Int>) {
        databaseHelper.writableDatabase.use { database ->
            list.forEach { id ->
                database.execSQL("DELETE FROM ${DatabaseHelper.TABLE_ACCOUNT_BOOK} WHERE ${DatabaseHelper.ACCOUNT_BOOK_COL_ID} = $id")
            }
        }
    }

    override fun deleteByCategoryId(list: List<Int>) {
        databaseHelper.writableDatabase.use { database ->
            list.forEach { id ->
                database.execSQL("DELETE FROM ${DatabaseHelper.TABLE_CATEGORY} WHERE ${DatabaseHelper.CATEGORY_COL_ID} = $id")
            }
        }
    }

    override fun deleteByPaymentId(list: List<Int>) {
        databaseHelper.writableDatabase.use { database ->
            list.forEach { id ->
                database.execSQL("DELETE FROM ${DatabaseHelper.TABLE_PAYMENT} WHERE ${DatabaseHelper.PAYMENT_COL_ID} = $id")
            }
        }
    }

    override fun insertCategory(name: String, color: String, isIncome: Boolean) {
        databaseHelper.writableDatabase.use { database ->
            val contentValues = ContentValues().apply {
                put(DatabaseHelper.CATEGORY_COL_NAME, name)
                put(DatabaseHelper.CATEGORY_COL_COLOR, color)
                put(DatabaseHelper.CATEGORY_COL_IS_INCOME, isIncome)
            }
            database.insert(DatabaseHelper.TABLE_CATEGORY, null, contentValues)
        }
    }

    override fun insertPayment(name: String) {
        databaseHelper.writableDatabase.use { database ->
            val contentValues = ContentValues().apply {
                put(DatabaseHelper.PAYMENT_COL_NAME, name)
            }
            database.insert(DatabaseHelper.TABLE_PAYMENT, null, contentValues)
        }
    }

    override fun insertHistory(
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