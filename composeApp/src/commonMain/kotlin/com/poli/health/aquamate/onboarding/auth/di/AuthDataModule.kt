package com.poli.health.aquamate.onboarding.auth.di

import com.poli.health.aquamate.onboarding.auth.data.repository.LoginRepository
import com.poli.health.aquamate.onboarding.auth.data.repository.LoginRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val authDataModule: Module = module {

    factory<LoginRepository> {
        LoginRepositoryImpl(
            authRemoteDataSource = get()
        )
    }
}
