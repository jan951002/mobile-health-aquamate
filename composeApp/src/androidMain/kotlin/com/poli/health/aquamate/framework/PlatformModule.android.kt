package com.poli.health.aquamate.framework

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.poli.health.aquamate.onboarding.auth.data.dao.SessionDao
import com.poli.health.aquamate.onboarding.auth.data.dao.SessionDaoImpl
import com.poli.health.aquamate.onboarding.profile.data.dao.UserProfileDao
import com.poli.health.aquamate.onboarding.profile.data.dao.UserProfileDaoImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule: Module = module {

    single<CoroutineDispatcher>(named("ioDispatcher")) { Dispatchers.IO }

    single<CoroutineDispatcher>(named("mainDispatcher")) { Dispatchers.Main }

    single<DataStore<Preferences>> {
        createDataStore {
            androidContext().filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
        }
    }

    single<SessionDao> {
        SessionDaoImpl(dataStore = get())
    }

    single<UserProfileDao> {
        UserProfileDaoImpl(dataStore = get())
    }
}
