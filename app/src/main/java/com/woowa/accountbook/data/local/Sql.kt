package com.woowa.accountbook.data.local

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
