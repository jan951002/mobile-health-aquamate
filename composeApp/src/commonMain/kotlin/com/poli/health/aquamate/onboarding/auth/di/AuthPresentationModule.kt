package com.poli.health.aquamate.onboarding.auth.di

import com.poli.health.aquamate.onboarding.auth.presentation.viewmodel.AuthViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authPresentationModule: Module = module {

    viewModel {
        AuthViewModel(
            signInWithEmailUseCase = get(),
            signUpWithEmailUseCase = get()
        )
    }
}
