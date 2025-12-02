package com.poli.health.aquamate.onboarding.profile.di

import org.koin.core.module.Module
import org.koin.dsl.module

val profileModule: Module = module {
    includes(
        profileDataModule,
        profileDomainModule,
        profilePresentationModule
    )
}
