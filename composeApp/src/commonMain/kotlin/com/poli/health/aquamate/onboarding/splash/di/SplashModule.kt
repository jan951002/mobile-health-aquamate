package com.poli.health.aquamate.onboarding.splash.di

import org.koin.core.module.Module
import org.koin.dsl.module

val splashModule: Module = module {
    includes(
        splashPresentationModule
    )
}
