package com.poli.health.aquamate

import android.app.Application
import com.poli.health.aquamate.framework.platformModule
import com.poli.health.aquamate.onboarding.auth.di.authModule
import com.poli.health.aquamate.onboarding.auth.di.intakeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AquaMateApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AquaMateApplication)
            modules(
                platformModule,
                authModule,
                intakeModule
            )
        }
    }
}
