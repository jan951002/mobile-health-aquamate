package com.poli.health.aquamate

import android.app.Application
import com.poli.health.aquamate.di.initKoin
import org.koin.android.ext.koin.androidContext

class AquaMateApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@AquaMateApplication)
        }
    }
}
