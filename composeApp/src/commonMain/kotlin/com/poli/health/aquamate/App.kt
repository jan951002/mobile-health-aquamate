package com.poli.health.aquamate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.poli.health.aquamate.onboarding.auth.presentation.screen.AuthScreen
import com.poli.health.aquamate.ui.theme.AquaMateTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AquaMateTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AuthScreen(
                onAuthSuccess = {
                    println("Authenticated successfully")
                }
            )
        }
    }
}
