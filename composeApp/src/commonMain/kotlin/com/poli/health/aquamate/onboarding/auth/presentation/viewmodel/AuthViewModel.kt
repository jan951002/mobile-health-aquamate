package com.poli.health.aquamate.onboarding.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poli.health.aquamate.onboarding.auth.domain.model.AuthState
import com.poli.health.aquamate.onboarding.auth.domain.usecase.SignInWithEmailUseCase
import com.poli.health.aquamate.onboarding.auth.domain.usecase.SignUpWithEmailUseCase
import com.poli.health.aquamate.onboarding.auth.presentation.model.AuthUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Input)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onSignIn(email: String, password: String) {
        viewModelScope.launch(ioDispatcher) {
            withContext(mainDispatcher) {
                _uiState.value = AuthUiState.Authenticating
            }

            val newState = when (val result = signInWithEmailUseCase(email, password)) {
                is AuthState.Authenticated -> AuthUiState.Success(result.user)
                is AuthState.NotAuthenticated -> AuthUiState.Error(
                    message = result.error ?: "Unknown error occurred"
                )

                AuthState.Loading -> AuthUiState.Authenticating
            }

            withContext(mainDispatcher) {
                _uiState.value = newState
            }
        }
    }

    fun onSignUp(email: String, password: String) {
        viewModelScope.launch(ioDispatcher) {
            withContext(mainDispatcher) {
                _uiState.value = AuthUiState.Authenticating
            }

            val newState = when (val result = signUpWithEmailUseCase(email, password)) {
                is AuthState.Authenticated -> AuthUiState.Success(result.user)
                is AuthState.NotAuthenticated -> AuthUiState.Error(
                    message = result.error ?: "Unknown error occurred"
                )

                AuthState.Loading -> AuthUiState.Authenticating
            }

            withContext(mainDispatcher) {
                _uiState.value = newState
            }
        }
    }

    fun clearError() {
        if (_uiState.value is AuthUiState.Error) {
            _uiState.value = AuthUiState.Input
        }
    }
}
