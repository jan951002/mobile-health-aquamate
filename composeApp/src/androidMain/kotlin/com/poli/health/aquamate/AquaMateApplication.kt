package com.poli.health.aquamate

import android.app.Application
import com.poli.health.aquamate.framework.platformModule
import com.poli.health.aquamate.onboarding.auth.di.authModule
// TODO: Descomentar cuando implementes el módulo userSetup
// import com.poli.health.aquamate.onboarding.usersetup.di.userSetupModule
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
                authModule
                // TODO: Descomentar cuando implementes userSetupModule
                // userSetupModule
            )
        }
    }
}
