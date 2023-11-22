package com.example.newsApp.main

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class NewsApp: Application() {
    val databaseModule = module{

    }
    val appModule = module {
        includes(databaseModule)
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}