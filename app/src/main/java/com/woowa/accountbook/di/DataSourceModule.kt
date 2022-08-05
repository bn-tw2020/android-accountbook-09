package com.woowa.accountbook.di

import com.woowa.accountbook.data.local.DatabaseHelper
import com.woowa.accountbook.data.local.category.CategoryDataSource
import com.woowa.accountbook.data.local.category.CategoryLocalDataSource
import com.woowa.accountbook.data.local.history.HistoryDataSource
import com.woowa.accountbook.data.local.history.HistoryLocalDataSource
import com.woowa.accountbook.data.local.payment.PaymentDataSource
import com.woowa.accountbook.data.local.payment.PaymentLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideHistoryDataSource(databaseHelper: DatabaseHelper): HistoryDataSource {
        return HistoryLocalDataSource(databaseHelper)
    }

    @Provides
    @Singleton
    fun provideCategoryDataSource(databaseHelper: DatabaseHelper): CategoryDataSource {
        return CategoryLocalDataSource(databaseHelper)
    }

    @Provides
    @Singleton
    fun providePaymentDataSource(databaseHelper: DatabaseHelper): PaymentDataSource {
        return PaymentLocalDataSource(databaseHelper)
    }
}