package com.woowa.accountbook

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AccountBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}