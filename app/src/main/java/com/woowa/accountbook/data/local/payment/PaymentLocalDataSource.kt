package com.woowa.accountbook.data.local.payment

import android.content.ContentValues
import com.woowa.accountbook.data.entitiy.Payment
import com.woowa.accountbook.data.local.DatabaseHelper
import javax.inject.Inject

class PaymentLocalDataSource @Inject constructor(
    private val databaseHelper: DatabaseHelper
) : PaymentDataSource {

    override suspend fun findById(id: Int): Payment? {
        databaseHelper.readableDatabase.use { database ->
            val sql =
                "SELECT * FROM ${DatabaseHelper.TABLE_PAYMENT} WHERE ${DatabaseHelper.PAYMENT_COL_ID} = ?"
            val cursor = database.rawQuery(sql, arrayOf(id.toString()))
            return cursor.use {
                if (it.moveToNext()) {
                    Payment(
                        id = it.getInt(0),
                        name = it.getString(1)
                    )
                } else null
            }
        }
    }

    override suspend fun findByName(name: String): Payment? {
        databaseHelper.readableDatabase.use { database ->
            val sql =
                "SELECT * FROM ${DatabaseHelper.TABLE_PAYMENT} WHERE ${DatabaseHelper.PAYMENT_COL_NAME} = ?"
            val cursor = database.rawQuery(sql, arrayOf(name))
            return cursor.use {
                if (it.moveToNext()) {
                    Payment(
                        id = it.getInt(0),
                        name = it.getString(1)
                    )
                } else null
            }
        }
    }

    override suspend fun findAll(): List<Payment> {
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

    override suspend fun deleteById(list: List<Int>) {
        databaseHelper.writableDatabase.use { database ->
            list.forEach { id ->
                database.execSQL("DELETE FROM ${DatabaseHelper.TABLE_PAYMENT} WHERE ${DatabaseHelper.PAYMENT_COL_ID} = $id")
            }
        }
    }

    override suspend fun update(id: Int, name: String) {
        databaseHelper.writableDatabase.use { database ->
            val contentValues = ContentValues().apply {
                put(DatabaseHelper.PAYMENT_COL_NAME, name)
            }
            database.update(DatabaseHelper.TABLE_PAYMENT, contentValues, "_id=$id", null)
        }
    }

    override suspend fun save(name: String) {
        databaseHelper.writableDatabase.use { database ->
            val contentValues = ContentValues().apply {
                put(DatabaseHelper.PAYMENT_COL_NAME, name)
            }
            database.insert(DatabaseHelper.TABLE_PAYMENT, null, contentValues)
        }
    }
}