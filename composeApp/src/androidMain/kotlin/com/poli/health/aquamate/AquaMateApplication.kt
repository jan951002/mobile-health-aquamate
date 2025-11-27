package com.poli.health.aquamate

import android.app.Application
import com.poli.health.aquamate.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class AquaMateApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AquaMateApplication)
        }
    }
}
