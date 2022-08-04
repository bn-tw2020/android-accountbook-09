package com.woowa.accountbook.data.local

import android.content.ContentValues

internal val createPaymentQuery = """
            CREATE TABLE ${DatabaseHelper.TABLE_PAYMENT} (
                ${DatabaseHelper.PAYMENT_COL_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, 
                ${DatabaseHelper.PAYMENT_COL_NAME} TEXT NOT NULL
            )
        """.trimIndent()

internal val createCategoryQuery = """
            CREATE TABLE ${DatabaseHelper.TABLE_CATEGORY} (
                ${DatabaseHelper.CATEGORY_COL_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                ${DatabaseHelper.CATEGORY_COL_IS_INCOME} BOOLEAN NOT NULL, 
                ${DatabaseHelper.CATEGORY_COL_NAME} TEXT NOT NULL, 
                ${DatabaseHelper.CATEGORY_COL_COLOR} TEXT NOT NULL
            )
        """.trimIndent()

internal val createAccountBookQuery = """
            CREATE TABLE ${DatabaseHelper.TABLE_ACCOUNT_BOOK} (
                ${DatabaseHelper.ACCOUNT_BOOK_COL_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                ${DatabaseHelper.ACCOUNT_BOOK_COL_MONEY} INTEGER NOT NULL,
                ${DatabaseHelper.ACCOUNT_BOOK_COL_CATEGORY} INTEGER,
                ${DatabaseHelper.ACCOUNT_BOOK_COL_PAYMENT} INTEGER NOT NULL, 
                ${DatabaseHelper.ACCOUNT_BOOK_COL_CONTENT} TEXT,
                ${DatabaseHelper.ACCOUNT_BOOK_COL_YEAR} INTEGER NOT NULL,
                ${DatabaseHelper.ACCOUNT_BOOK_COL_MONTH} INTEGER NOT NULL,
                ${DatabaseHelper.ACCOUNT_BOOK_COL_DAY} INTEGER NOT NULL,
                FOREIGN KEY (${DatabaseHelper.ACCOUNT_BOOK_COL_PAYMENT}) REFERENCES ${DatabaseHelper.TABLE_PAYMENT}(${DatabaseHelper.PAYMENT_COL_ID}),
                FOREIGN KEY (${DatabaseHelper.ACCOUNT_BOOK_COL_CATEGORY}) REFERENCES ${DatabaseHelper.TABLE_CATEGORY}(${DatabaseHelper.CATEGORY_COL_ID})
            )
        """.trimIndent()

fun DatabaseHelper.initializeData() {
    savePayment("현대카드")
    savePayment("카카오뱅크 체크카드")
    saveCategory("교통", "#94D3CC", false)
    saveCategory("문화/여가", "#D092E2", false)
    saveCategory("미분류", "#817DCE", false)
    saveCategory("생활", "#4A6CC3", false)
    saveCategory("쇼핑/뷰티", "#4CB8B8", false)
    saveCategory("식비", "#4CA1DE", false)
    saveCategory("의료/건강", "#6ED5EB", false)
    saveCategory("월급", "#9BD182", true)
    saveCategory("용돈", "#EDCF65", true)
    saveCategory("기타", "#E29C4D", true)
}

fun DatabaseHelper.savePayment(name: String) {
    writableDatabase.use { database ->
        val contentValues = ContentValues().apply {
            put(DatabaseHelper.PAYMENT_COL_NAME, name)
        }
        database.insert(DatabaseHelper.TABLE_PAYMENT, null, contentValues)
    }
}

fun DatabaseHelper.saveCategory(name: String, color: String, isIncome: Boolean) {
    writableDatabase.use { database ->
        val contentValues = ContentValues().apply {
            put(DatabaseHelper.CATEGORY_COL_NAME, name)
            put(DatabaseHelper.CATEGORY_COL_COLOR, color)
            put(DatabaseHelper.CATEGORY_COL_IS_INCOME, isIncome)
        }
        database.insert(DatabaseHelper.TABLE_CATEGORY, null, contentValues)
    }
}