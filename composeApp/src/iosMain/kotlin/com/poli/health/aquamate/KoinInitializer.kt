package com.poli.health.aquamate

import com.poli.health.aquamate.framework.platformModule
import com.poli.health.aquamate.onboarding.auth.di.authModule
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatformTools

fun initKoin() {
    if (KoinPlatformTools.defaultContext().getOrNull() == null) {
        startKoin {
            modules(
                platformModule,
                authModule
            )
        }
    }
}

