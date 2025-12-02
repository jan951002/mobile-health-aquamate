package com.poli.health.aquamate.intake.di

import com.poli.health.aquamate.intake.data.datasource.WaterIntakeDataSource
import com.poli.health.aquamate.intake.data.datasource.WaterIntakeLocalDataSource
import com.poli.health.aquamate.intake.data.mapper.WaterIntakeMapper
import com.poli.health.aquamate.intake.data.mapper.WaterIntakeMapperImpl
import com.poli.health.aquamate.intake.data.repository.WaterIntakeRepository
import com.poli.health.aquamate.intake.data.repository.WaterIntakeRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val intakeDataModule: Module = module {

    factory<WaterIntakeMapper> {
        WaterIntakeMapperImpl()
    }

    single<WaterIntakeDataSource> {
        WaterIntakeLocalDataSource()
    }

    factory<WaterIntakeRepository> {
        WaterIntakeRepositoryImpl(
            dataSource = get(),
            mapper = get()
        )
    }
}
