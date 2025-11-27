package com.poli.health.aquamate.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.poli.health.aquamate.onboarding.auth.domain.model.AuthState
import com.poli.health.aquamate.onboarding.auth.domain.usecase.ObserveAuthStateUseCase
import com.poli.health.aquamate.onboarding.auth.presentation.screen.AuthScreen
import com.poli.health.aquamate.onboarding.auth.presentation.screen.IntakeScreen
import com.poli.health.aquamate.onboarding.auth.presentation.viewmodel.IntakeViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Route.Auth> {
            AuthScreen(
                onAuthSuccess = {
                    navController.navigate(Route.Intake) {
                        popUpTo(Route.Auth) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Route.Intake> {
            val viewModel: IntakeViewModel = koinViewModel()
            val state by viewModel.state.collectAsState()

            IntakeScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
}
