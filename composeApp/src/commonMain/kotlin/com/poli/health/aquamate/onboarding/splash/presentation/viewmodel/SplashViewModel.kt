package com.poli.health.aquamate.onboarding.splash.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poli.health.aquamate.navigation.Route
import com.poli.health.aquamate.onboarding.auth.domain.usecase.GetCurrentUserIdUseCase
import com.poli.health.aquamate.onboarding.auth.domain.usecase.IsUserLoggedInUseCase
import com.poli.health.aquamate.onboarding.profile.domain.usecase.HasUserProfileUseCase
import com.poli.health.aquamate.onboarding.splash.presentation.model.SplashUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SplashViewModel(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val hasUserProfileUseCase: HasUserProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SplashUiState())
    val state: StateFlow<SplashUiState> = _state.asStateFlow()

    fun determineNavigation() {
        viewModelScope.launch {
            delay(2000)

            try {
                val isLoggedIn = isUserLoggedInUseCase()
                val destination = when {
                    !isLoggedIn -> Route.Auth
                    else -> {
                        val userId = getCurrentUserIdUseCase().first()
                        if (userId != null) {
                            val hasProfile = hasUserProfileUseCase(userId)
                            if (hasProfile) Route.Intake else Route.Profile
                        } else {
                            Route.Auth
                        }
                    }
                }

                _state.update {
                    it.copy(
                        isLoading = false,
                        navigationDestination = destination
                    )
                }
            } catch (_: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        navigationDestination = Route.Auth
                    )
                }
            }
        }
    }
}
