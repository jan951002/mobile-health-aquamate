package com.poli.health.aquamate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.poli.health.aquamate.framework.platformModule
import com.poli.health.aquamate.onboarding.auth.di.authModule
import com.poli.health.aquamate.onboarding.auth.presentation.screen.LoginScreen
import com.poli.health.aquamate.ui.theme.AquaMateTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = {

        modules(
            platformModule,
            authModule
        )
    }) {
        AquaMateTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                LoginScreen(
                    onLoginSuccess = {
                        println("Logged with emailλ")
                    }
                )
            }
        }
    }
}
