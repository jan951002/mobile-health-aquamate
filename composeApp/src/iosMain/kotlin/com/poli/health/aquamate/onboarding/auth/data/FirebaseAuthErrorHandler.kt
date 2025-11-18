package com.poli.health.aquamate.onboarding.auth.data

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError

@OptIn(ExperimentalForeignApi::class)
internal interface FirebaseAuthErrorHandler {

    fun getErrorMessage(error: NSError): String
}
