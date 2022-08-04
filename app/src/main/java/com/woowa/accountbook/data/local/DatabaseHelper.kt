package com.woowa.accountbook.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import javax.inject.Inject


class DatabaseHelper @Inject constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(database: SQLiteDatabase?) {
        database?.apply {
            initializeData()
            execSQL("DROP TABLE IF EXISTS $TABLE_PAYMENT")
            execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORY")
            execSQL("DROP TABLE IF EXISTS $TABLE_ACCOUNT_BOOK")
            execSQL(createPaymentQuery)
            execSQL(createCategoryQuery)
            execSQL(createAccountBookQuery)
        }
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            database?.apply {
                execSQL("DROP TABLE IF EXISTS $TABLE_PAYMENT")
                execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORY")
                execSQL("DROP TABLE IF EXISTS $TABLE_ACCOUNT_BOOK")
            }
            onCreate(database)
        }
    }

    companion object {
        const val DATABASE_NAME = "AccountBook.db"
        const val DATABASE_VERSION = 1

        const val TABLE_ACCOUNT_BOOK = "account_book"
        const val ACCOUNT_BOOK_COL_ID = "_id"
        const val ACCOUNT_BOOK_COL_MONEY = "money"
        const val ACCOUNT_BOOK_COL_CATEGORY = "category"
        const val ACCOUNT_BOOK_COL_CONTENT = "content"
        const val ACCOUNT_BOOK_COL_YEAR = "year"
        const val ACCOUNT_BOOK_COL_MONTH = "month"
        const val ACCOUNT_BOOK_COL_DAY = "day"
        const val ACCOUNT_BOOK_COL_PAYMENT = "payment"

        const val TABLE_CATEGORY = "category"
        const val CATEGORY_COL_ID = "_id"
        const val CATEGORY_COL_NAME = "name"
        const val CATEGORY_COL_COLOR = "color"
        const val CATEGORY_COL_IS_INCOME = "is_income"

        const val TABLE_PAYMENT = "payment"
        const val PAYMENT_COL_ID = "_id"
        const val PAYMENT_COL_NAME = "name"
    }
}