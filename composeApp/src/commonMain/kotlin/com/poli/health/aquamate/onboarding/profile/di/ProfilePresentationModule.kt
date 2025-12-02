package com.poli.health.aquamate.onboarding.profile.di

import com.poli.health.aquamate.onboarding.profile.presentation.viewmodel.UserProfileViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val profilePresentationModule: Module = module {

    viewModel {
        UserProfileViewModel(
            saveUserProfileUseCase = get(),
            getCurrentUserIdUseCase = get(),
            calculateWaterIntakeUseCase = get(),
            calculateBmiUseCase = get(),
            updateProfileCompleteUseCase = get(),
            ioDispatcher = get(named("ioDispatcher")),
            mainDispatcher = get(named("mainDispatcher"))
        )
    }
}
