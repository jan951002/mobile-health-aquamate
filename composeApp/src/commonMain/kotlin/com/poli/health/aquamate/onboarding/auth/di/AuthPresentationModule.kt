package com.poli.health.aquamate.onboarding.auth.di

import com.poli.health.aquamate.onboarding.auth.presentation.viewmodel.LoginViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authPresentationModule: Module = module {

    viewModel {
        LoginViewModel(
            signInWithEmailUseCase = get(),
            ioDispatcher = get(named("ioDispatcher")),
            mainDispatcher = get(named("mainDispatcher"))
        )
    }
}
