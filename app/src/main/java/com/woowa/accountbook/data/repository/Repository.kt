package com.woowa.accountbook.data.repository

import com.woowa.accountbook.data.entitiy.History
import com.woowa.accountbook.data.local.LocalDataSource
import javax.inject.Inject

class Repository @Inject constructor(private val dataSource: LocalDataSource) {

    fun getHistory(month: Int): Result<List<History>> {
        return runCatching { dataSource.findByHistoryMonth(month.toString()) }
    }
}