package com.poli.health.aquamate.onboarding.auth.di

import com.poli.health.aquamate.onboarding.auth.data.datasource.WaterIntakeDataSource
import com.poli.health.aquamate.onboarding.auth.data.repository.WaterIntakeRepositoryImpl
import com.poli.health.aquamate.onboarding.auth.data.repository.WaterIntakeRepository
import com.poli.health.aquamate.onboarding.auth.domain.usecase.*
import com.poli.health.aquamate.onboarding.auth.presentation.viewmodel.IntakeViewModel
import kotlinx.coroutines.CoroutineScope

object IntakeModule {

    fun provideWaterIntakeRepository(
        dataSource: WaterIntakeDataSource
    ): WaterIntakeRepository {
        return WaterIntakeRepositoryImpl(dataSource)
    }

    fun provideRegisterWaterIntakeUseCase(
        repository: WaterIntakeRepository
    ): RegisterWaterIntakeUseCase {
        return RegisterWaterIntakeUseCase(repository)
    }

    fun provideGetLastIntakeUseCase(
        repository: WaterIntakeRepository
    ): GetLastIntakeUseCase {
        return GetLastIntakeUseCase(repository)
    }

    fun provideDeleteLastIntakeUseCase(
        repository: WaterIntakeRepository
    ): DeleteLastIntakeUseCase {
        return DeleteLastIntakeUseCase(repository)
    }

    fun provideGetTodayTotalUseCase(
        repository: WaterIntakeRepository
    ): GetTodayTotalUseCase {
        return GetTodayTotalUseCase(repository)
    }

    fun provideIntakeViewModel(
        registerUseCase: RegisterWaterIntakeUseCase,
        getLastUseCase: GetLastIntakeUseCase,
        deleteUseCase: DeleteLastIntakeUseCase,
        getTotalUseCase: GetTodayTotalUseCase,
        coroutineScope: CoroutineScope
    ): IntakeViewModel {
        return IntakeViewModel(
            registerUseCase,
            getLastUseCase,
            deleteUseCase,
            getTotalUseCase,
            coroutineScope
        )
    }
}