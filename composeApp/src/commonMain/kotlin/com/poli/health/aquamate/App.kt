package com.poli.health.aquamate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.poli.health.aquamate.navigation.NavGraph
import com.poli.health.aquamate.navigation.Route
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
            val navController = rememberNavController()

            NavGraph(
                navController = navController,
                startDestination = Route.Splash
            )
        }
    }
}
