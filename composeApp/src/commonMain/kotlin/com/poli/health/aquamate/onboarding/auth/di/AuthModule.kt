package com.poli.health.aquamate.onboarding.auth.di

import org.koin.core.module.Module
import org.koin.dsl.module

val authModule: Module = module {
    includes(
        authDataModule,
        authDomainModule,
        authPresentationModule
    )
}

