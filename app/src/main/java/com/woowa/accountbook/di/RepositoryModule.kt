package com.woowa.accountbook.di

import com.woowa.accountbook.data.local.category.CategoryDataSource
import com.woowa.accountbook.data.local.history.HistoryDataSource
import com.woowa.accountbook.data.local.payment.PaymentDataSource
import com.woowa.accountbook.data.repository.CategoryRepositoryImpl
import com.woowa.accountbook.data.repository.HistoryRepositoryImpl
import com.woowa.accountbook.data.repository.PaymentRepositoryImpl
import com.woowa.accountbook.domain.repository.category.CategoryRepository
import com.woowa.accountbook.domain.repository.history.HistoryRepository
import com.woowa.accountbook.domain.repository.payment.PaymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideHistoryRepository(
        historyDataSource: HistoryDataSource,
        categoryDataSource: CategoryDataSource,
        paymentDataSource: PaymentDataSource,
        ioDispatcher: CoroutineDispatcher
    ): HistoryRepository {
        return HistoryRepositoryImpl(
            historyDataSource,
            categoryDataSource,
            paymentDataSource,
            ioDispatcher
        )
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        historyDataSource: HistoryDataSource,
        categoryDataSource: CategoryDataSource,
        ioDispatcher: CoroutineDispatcher
    ): CategoryRepository {
        return CategoryRepositoryImpl(historyDataSource, categoryDataSource, ioDispatcher)
    }

    @Provides
    @Singleton
    fun providePaymentRepository(
        historyDataSource: HistoryDataSource,
        paymentDataSource: PaymentDataSource,
        ioDispatcher: CoroutineDispatcher
    ): PaymentRepository {
        return PaymentRepositoryImpl(historyDataSource, paymentDataSource, ioDispatcher)
    }
}