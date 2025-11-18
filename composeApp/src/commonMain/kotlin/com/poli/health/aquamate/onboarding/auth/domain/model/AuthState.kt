package com.poli.health.aquamate.onboarding.auth.domain.model

sealed interface AuthState {

    data object Loading : AuthState

    data class Authenticated(val user: User) : AuthState

    data class NotAuthenticated(val error: String? = null) : AuthState
}
