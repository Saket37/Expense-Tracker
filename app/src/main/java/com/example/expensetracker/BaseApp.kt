package com.example.expensetracker

import android.app.Application
import com.example.expensetracker.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApp)
            modules(
                databaseModule
            )
        }
    }
}