package com.poli.health.aquamate.intake.di

import com.poli.health.aquamate.intake.data.datasource.WaterIntakeDataSource
import com.poli.health.aquamate.intake.data.datasource.WaterIntakeFirestoreDataSource
import com.poli.health.aquamate.intake.data.datasource.WaterIntakeLocalDataSource
import com.poli.health.aquamate.intake.data.datasource.WaterIntakeRemoteDataSource
import com.poli.health.aquamate.intake.data.error.IntakeFirestoreErrorHandler
import com.poli.health.aquamate.intake.data.error.IntakeFirestoreErrorHandlerImpl
import com.poli.health.aquamate.intake.data.mapper.DailyIntakeMapper
import com.poli.health.aquamate.intake.data.mapper.DailyIntakeMapperImpl
import com.poli.health.aquamate.intake.data.mapper.WaterIntakeMapper
import com.poli.health.aquamate.intake.data.mapper.WaterIntakeMapperImpl
import com.poli.health.aquamate.intake.data.repository.WaterIntakeRepository
import com.poli.health.aquamate.intake.data.repository.WaterIntakeRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val intakeDataModule: Module = module {

    factory<IntakeFirestoreErrorHandler> {
        IntakeFirestoreErrorHandlerImpl()
    }

    factory<WaterIntakeMapper> {
        WaterIntakeMapperImpl()
    }

    factory<DailyIntakeMapper> {
        DailyIntakeMapperImpl()
    }

    single<WaterIntakeDataSource> {
        WaterIntakeLocalDataSource()
    }

    single<WaterIntakeRemoteDataSource> {
        WaterIntakeFirestoreDataSource(
            firestore = get(),
            ioDispatcher = get(named("ioDispatcher")),
            errorHandler = get()
        )
    }

    factory<WaterIntakeRepository> {
        WaterIntakeRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get(),
            waterIntakeMapper = get(),
            dailyIntakeMapper = get()
        )
    }
}
