package com.jess.challenge.exchangerates.ui

import android.app.Application
import com.jess.challenge.exchangerates.ui.injection.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EuroExchangeApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@EuroExchangeApp)
            modules(applicationModule)
        }
    }
}