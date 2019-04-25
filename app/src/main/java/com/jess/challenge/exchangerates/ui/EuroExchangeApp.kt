package com.jess.challenge.exchangerates.ui

import android.app.Application
import com.jess.challenge.exchangerates.ui.injection.applicationModule
import com.jess.challenge.exchangerates.ui.injection.browseModule
import org.koin.android.ext.android.startKoin

class EuroExchangeApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(applicationModule, browseModule))
    }
}