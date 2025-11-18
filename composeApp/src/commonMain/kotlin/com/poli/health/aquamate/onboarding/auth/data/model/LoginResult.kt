package com.poli.health.aquamate.onboarding.auth.data.model

sealed interface LoginResult {

    data object Loading : LoginResult

    data class Success(val user: AuthUser) : LoginResult

    data class Error(val message: String = "", val cause: Throwable? = null) : LoginResult
}
