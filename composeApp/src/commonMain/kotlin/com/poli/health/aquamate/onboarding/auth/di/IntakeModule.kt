package com.poli.health.aquamate.onboarding.auth.di

import org.koin.core.module.Module
import org.koin.dsl.module

val intakeModule: Module = module {
    includes(
        intakeDataModule,
        intakeDomainModule,
        intakePresentationModule
    )
}
