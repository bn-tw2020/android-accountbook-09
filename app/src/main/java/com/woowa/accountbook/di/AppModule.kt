package com.woowa.accountbook.di

import com.woowa.accountbook.ui.calendar.CustomCalendar
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCustomCalendar(): CustomCalendar {
        return CustomCalendar()
    }
}