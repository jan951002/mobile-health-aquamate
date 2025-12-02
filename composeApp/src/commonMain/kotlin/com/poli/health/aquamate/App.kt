package com.poli.health.aquamate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.poli.health.aquamate.navigation.NavGraph
import com.poli.health.aquamate.navigation.Route
import com.poli.health.aquamate.onboarding.auth.domain.usecase.GetCurrentUserIdUseCase
import com.poli.health.aquamate.onboarding.auth.domain.usecase.IsUserLoggedInUseCase
import com.poli.health.aquamate.onboarding.profile.domain.usecase.HasUserProfileUseCase
import com.poli.health.aquamate.ui.theme.AquaMateTheme
import kotlinx.coroutines.flow.first
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    AquaMateTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            val isUserLoggedInUseCase: IsUserLoggedInUseCase = koinInject()
            val getCurrentUserIdUseCase: GetCurrentUserIdUseCase = koinInject()
            val hasUserProfileUseCase: HasUserProfileUseCase = koinInject()
            val startDestination = remember { mutableStateOf<Route?>(null) }

            LaunchedEffect(Unit) {
                val isLoggedIn = isUserLoggedInUseCase()
                startDestination.value = when {
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
            }

            startDestination.value?.let { destination ->
                NavGraph(
                    navController = navController,
                    startDestination = destination
                )
            }
        }
    }
}
