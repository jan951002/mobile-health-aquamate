package com.poli.health.aquamate.intake.di

import com.poli.health.aquamate.intake.domain.usecase.DeleteLastIntakeUseCase
import com.poli.health.aquamate.intake.domain.usecase.DeleteLastIntakeUseCaseImpl
import com.poli.health.aquamate.intake.domain.usecase.GetLastIntakeUseCase
import com.poli.health.aquamate.intake.domain.usecase.GetLastIntakeUseCaseImpl
import com.poli.health.aquamate.intake.domain.usecase.GetTodayTotalUseCase
import com.poli.health.aquamate.intake.domain.usecase.GetTodayTotalUseCaseImpl
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
}
