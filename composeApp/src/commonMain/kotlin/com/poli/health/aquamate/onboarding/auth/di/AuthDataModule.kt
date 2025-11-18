package com.poli.health.aquamate.onboarding.auth.di

import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthLocalDataSource
import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthLocalDataSourceImpl
import com.poli.health.aquamate.onboarding.auth.data.repository.AuthRepository
import com.poli.health.aquamate.onboarding.auth.data.repository.AuthRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authDataModule: Module = module {

    single<AuthLocalDataSource> {
        AuthLocalDataSourceImpl(
            sessionDao = get(),
            ioDispatcher = get(named("ioDispatcher"))
        )
    }

    factory<AuthRepository> {
        AuthRepositoryImpl(
            authRemoteDataSource = get(),
            authLocalDataSource = get()
        )
    }
}
