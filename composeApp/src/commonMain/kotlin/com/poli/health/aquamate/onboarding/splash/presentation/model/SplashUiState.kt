package com.poli.health.aquamate.onboarding.splash.presentation.model

import com.poli.health.aquamate.navigation.Route

data class SplashUiState(
    val isLoading: Boolean = true,
    val navigationDestination: Route? = null
)
