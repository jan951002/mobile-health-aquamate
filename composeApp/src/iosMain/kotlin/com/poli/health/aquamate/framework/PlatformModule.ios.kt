package com.poli.health.aquamate.framework

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import cocoapods.FirebaseAuth.FIRAuth
import com.poli.health.aquamate.onboarding.auth.data.FirebaseAuthErrorHandler
import com.poli.health.aquamate.onboarding.auth.data.FirebaseAuthErrorHandlerImpl
import com.poli.health.aquamate.onboarding.auth.data.IOSAuthFirebaseDataSource
import com.poli.health.aquamate.onboarding.auth.data.datasource.AuthRemoteDataSource
import com.poli.health.aquamate.onboarding.auth.data.dao.SessionDao
import com.poli.health.aquamate.onboarding.auth.data.dao.SessionDaoImpl
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSURL

@OptIn(ExperimentalForeignApi::class)
actual val platformModule: Module = module {

    single<FIRAuth> { FIRAuth.auth() }

    factory<FirebaseAuthErrorHandler> { FirebaseAuthErrorHandlerImpl() }

    single<CoroutineDispatcher>(named("ioDispatcher")) { Dispatchers.IO }

    single<CoroutineDispatcher>(named("mainDispatcher")) { Dispatchers.Main }

    single<AuthRemoteDataSource> {
        IOSAuthFirebaseDataSource(
            firebaseAuth = get(),
            ioDispatcher = get(named("ioDispatcher")),
            errorHandler = get()
        )
    }

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
}
