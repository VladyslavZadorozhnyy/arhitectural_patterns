package com.example.cleanarchitectureexample

import android.app.Application
import com.example.cleanarchitectureexample.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CoinApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CoinApplication)
            modules(appModule)
        }
    }
}