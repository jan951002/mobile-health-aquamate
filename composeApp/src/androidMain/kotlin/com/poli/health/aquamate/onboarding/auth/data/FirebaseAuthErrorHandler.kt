package com.poli.health.aquamate.onboarding.auth.data

import com.google.firebase.auth.FirebaseAuthException

internal interface FirebaseAuthErrorHandler {

    fun getErrorMessage(exception: FirebaseAuthException): String
}
