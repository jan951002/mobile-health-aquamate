package com.poli.health.aquamate.framework

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.poli.health.aquamate.onboarding.auth.data.dao.SessionDao
import com.poli.health.aquamate.onboarding.auth.data.dao.SessionDaoImpl
import com.poli.health.aquamate.onboarding.profile.data.dao.UserProfileDao
import com.poli.health.aquamate.onboarding.profile.data.dao.UserProfileDaoImpl
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val platformModule: Module = module {

    single<CoroutineDispatcher>(named("ioDispatcher")) { Dispatchers.IO }

    single<CoroutineDispatcher>(named("mainDispatcher")) { Dispatchers.Main }

    single<DataStore<Preferences>> {
        createDataStore {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )
            requireNotNull(documentDirectory).path + "/$DATA_STORE_FILE_NAME"
        }
    }

    single<SessionDao> {
        SessionDaoImpl(dataStore = get())
    }

    single<UserProfileDao> {
        UserProfileDaoImpl(dataStore = get())
    }
}
