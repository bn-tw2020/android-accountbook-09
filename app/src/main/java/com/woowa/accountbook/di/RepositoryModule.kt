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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideHistoryRepository(historyDataSource: HistoryDataSource): HistoryRepository {
        return HistoryRepositoryImpl(historyDataSource)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDataSource: CategoryDataSource): CategoryRepository {
        return CategoryRepositoryImpl(categoryDataSource)
    }

    @Provides
    @Singleton
    fun providePaymentRepository(paymentDataSource: PaymentDataSource): PaymentRepository {
        return PaymentRepositoryImpl(paymentDataSource)
    }
}