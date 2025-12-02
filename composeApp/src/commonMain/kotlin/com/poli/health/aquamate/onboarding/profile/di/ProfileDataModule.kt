package com.poli.health.aquamate.onboarding.profile.di

import com.poli.health.aquamate.onboarding.profile.data.datasource.UserProfileFirestoreDataSource
import com.poli.health.aquamate.onboarding.profile.data.datasource.UserProfileLocalDataSource
import com.poli.health.aquamate.onboarding.profile.data.datasource.UserProfileLocalDataSourceImpl
import com.poli.health.aquamate.onboarding.profile.data.datasource.UserProfileRemoteDataSource
import com.poli.health.aquamate.onboarding.profile.data.error.FirestoreErrorHandler
import com.poli.health.aquamate.onboarding.profile.data.error.FirestoreErrorHandlerImpl
import com.poli.health.aquamate.onboarding.profile.data.mapper.UserProfileMapper
import com.poli.health.aquamate.onboarding.profile.data.mapper.UserProfileMapperImpl
import com.poli.health.aquamate.onboarding.profile.data.repository.UserProfileRepositoryImpl
import com.poli.health.aquamate.onboarding.profile.domain.repository.UserProfileRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val profileDataModule: Module = module {

    single { Firebase.firestore }

    factory<FirestoreErrorHandler> {
        FirestoreErrorHandlerImpl()
    }

    single<UserProfileLocalDataSource> {
        UserProfileLocalDataSourceImpl(
            userProfileDao = get(),
            ioDispatcher = get(named("ioDispatcher"))
        )
    }

    single<UserProfileRemoteDataSource> {
        UserProfileFirestoreDataSource(
            firestore = get(),
            ioDispatcher = get(named("ioDispatcher")),
            errorHandler = get()
        )
    }

    factory<UserProfileMapper> {
        UserProfileMapperImpl()
    }

    factory<UserProfileRepository> {
        UserProfileRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get(),
            mapper = get(),
            calculateWaterIntakeUseCase = get(),
            calculateBmiUseCase = get(),
            calculateBmrUseCase = get(),
            calculateTotalEnergyExpenditureUseCase = get()
        )
    }
}
