package com.poli.health.aquamate.onboarding.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poli.health.aquamate.onboarding.auth.domain.model.AuthState
import com.poli.health.aquamate.onboarding.auth.domain.usecase.SignInWithEmailUseCase
import com.poli.health.aquamate.onboarding.auth.presentation.model.LoginUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch(ioDispatcher) {
            withContext(mainDispatcher) {
                _uiState.value = LoginUiState.Loading
            }

            val result = signInWithEmailUseCase(email, password)

            val newState = when (result) {
                is AuthState.Authenticated -> LoginUiState.Success(result.user)
                is AuthState.NotAuthenticated -> LoginUiState.Error(
                    message = result.error ?: "Unknown error occurred"
                )
                AuthState.Loading -> LoginUiState.Loading
            }

            withContext(mainDispatcher) {
                _uiState.value = newState
            }
        }
    }

    fun clearError() {
        if (_uiState.value is LoginUiState.Error) {
            _uiState.value = LoginUiState.Idle
        }
    }
}
