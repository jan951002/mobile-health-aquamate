package com.poli.health.aquamate

import com.poli.health.aquamate.framework.platformModule
import com.poli.health.aquamate.onboarding.auth.di.authModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            platformModule,
            authModule
        )
    }
}

