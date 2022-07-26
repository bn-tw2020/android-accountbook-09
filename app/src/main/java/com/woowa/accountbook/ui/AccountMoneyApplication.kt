package com.woowa.accountbook.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AccountMoneyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}