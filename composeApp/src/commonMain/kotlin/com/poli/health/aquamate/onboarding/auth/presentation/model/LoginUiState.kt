package com.poli.health.aquamate.onboarding.auth.presentation.model

import com.poli.health.aquamate.onboarding.auth.domain.model.User

sealed interface LoginUiState {

    data object Idle : LoginUiState

    data object Loading : LoginUiState

    data class Success(val user: User) : LoginUiState

    data class Error(val message: String) : LoginUiState
}
