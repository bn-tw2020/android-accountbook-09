package com.woowa.accountbook.di

import com.woowa.accountbook.data.local.DataSource
import com.woowa.accountbook.data.local.DatabaseHelper
import com.woowa.accountbook.data.local.LocalDataSource
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
    fun provideAccountBookDataSource(databaseHelper: DatabaseHelper): DataSource {
        return LocalDataSource(databaseHelper)
    }
}