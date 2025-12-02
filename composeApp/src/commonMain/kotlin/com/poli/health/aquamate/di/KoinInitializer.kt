package com.poli.health.aquamate.di

import com.poli.health.aquamate.framework.platformModule
import com.poli.health.aquamate.onboarding.auth.di.authModule
import com.poli.health.aquamate.onboarding.auth.di.intakeModule
import com.poli.health.aquamate.onboarding.profile.di.profileModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

val sharedModules = listOf(
    platformModule,
    authModule,
    profileModule,
    intakeModule
)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(sharedModules)
    }
}
