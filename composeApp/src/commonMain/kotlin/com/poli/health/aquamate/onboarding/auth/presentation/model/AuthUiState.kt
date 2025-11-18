package com.poli.health.aquamate.onboarding.auth.presentation.model

import com.poli.health.aquamate.onboarding.auth.domain.model.User

sealed interface AuthUiState {

    data object Input : AuthUiState

    data object Authenticating : AuthUiState

    data class Success(val user: User) : AuthUiState

    data class Error(val message: String) : AuthUiState
}
