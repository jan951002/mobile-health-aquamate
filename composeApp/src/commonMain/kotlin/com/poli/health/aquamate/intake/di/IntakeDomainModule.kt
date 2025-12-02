package com.poli.health.aquamate.intake.di

import com.poli.health.aquamate.intake.domain.usecase.DeleteIntakeByIdUseCase
import com.poli.health.aquamate.intake.domain.usecase.DeleteIntakeByIdUseCaseImpl
import com.poli.health.aquamate.intake.domain.usecase.DeleteLastIntakeUseCase
import com.poli.health.aquamate.intake.domain.usecase.DeleteLastIntakeUseCaseImpl
import com.poli.health.aquamate.intake.domain.usecase.GetDailyIntakeUseCase
import com.poli.health.aquamate.intake.domain.usecase.GetDailyIntakeUseCaseImpl
import com.poli.health.aquamate.intake.domain.usecase.GetLastIntakeUseCase
import com.poli.health.aquamate.intake.domain.usecase.GetLastIntakeUseCaseImpl
import com.poli.health.aquamate.intake.domain.usecase.GetTodayTotalUseCase
import com.poli.health.aquamate.intake.domain.usecase.GetTodayTotalUseCaseImpl
import com.poli.health.aquamate.intake.domain.usecase.GetUserDailyGoalUseCase
import com.poli.health.aquamate.intake.domain.usecase.GetUserDailyGoalUseCaseImpl
import com.poli.health.aquamate.intake.domain.usecase.GetWeeklyStatsUseCase
import com.poli.health.aquamate.intake.domain.usecase.GetWeeklyStatsUseCaseImpl
import com.poli.health.aquamate.intake.domain.usecase.RegisterWaterIntakeUseCase
import com.poli.health.aquamate.intake.domain.usecase.RegisterWaterIntakeUseCaseImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val intakeDomainModule: Module = module {

    factory<RegisterWaterIntakeUseCase> {
        RegisterWaterIntakeUseCaseImpl(
            repository = get()
        )
    }

    factory<GetLastIntakeUseCase> {
        GetLastIntakeUseCaseImpl(
            repository = get()
        )
    }

    factory<GetTodayTotalUseCase> {
        GetTodayTotalUseCaseImpl(
            repository = get()
        )
    }

    factory<DeleteLastIntakeUseCase> {
        DeleteLastIntakeUseCaseImpl(
            repository = get()
        )
    }

    factory<GetUserDailyGoalUseCase> {
        GetUserDailyGoalUseCaseImpl(
            getUserProfileUseCase = get()
        )
    }

    factory<GetDailyIntakeUseCase> {
        GetDailyIntakeUseCaseImpl(
            repository = get()
        )
    }

    factory<GetWeeklyStatsUseCase> {
        GetWeeklyStatsUseCaseImpl(
            repository = get()
        )
    }

    factory<DeleteIntakeByIdUseCase> {
        DeleteIntakeByIdUseCaseImpl(
            repository = get()
        )
    }
}
