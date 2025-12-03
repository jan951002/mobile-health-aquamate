package com.poli.health.aquamate.onboarding.splash.di

import com.poli.health.aquamate.onboarding.splash.presentation.viewmodel.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val splashPresentationModule = module {
    viewModel {
        SplashViewModel(
            isUserLoggedInUseCase = get(),
            getCurrentUserIdUseCase = get(),
            hasUserProfileUseCase = get()
        )
    }
}
