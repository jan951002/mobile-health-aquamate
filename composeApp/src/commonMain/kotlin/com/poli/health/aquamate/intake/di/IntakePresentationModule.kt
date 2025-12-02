package com.poli.health.aquamate.intake.di

import com.poli.health.aquamate.intake.presentation.viewmodel.IntakeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val intakePresentationModule: Module = module {

    viewModel {
        IntakeViewModel(
            registerWaterIntakeUseCase = get(),
            getDailyIntakeUseCase = get(),
            getUserDailyGoalUseCase = get(),
            getWeeklyStatsUseCase = get(),
            deleteIntakeByIdUseCase = get(),
            getCurrentUserIdUseCase = get()
        )
    }
}
