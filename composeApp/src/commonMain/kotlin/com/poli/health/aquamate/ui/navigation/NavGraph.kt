package com.poli.health.aquamate.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.poli.health.aquamate.ui.screens.LanguageScreen
import com.poli.health.aquamate.ui.screens.ProfileScreen
import com.poli.health.aquamate.ui.screens.SettingsScreen

@Composable
fun AppNavGraph() {
    val nav = rememberNavController()

    NavHost(nav, startDestination = Screen.Settings.route) {

        composable(Screen.Settings.route) {
            SettingsScreen(nav)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(nav)
        }

        composable(Screen.Language.route) {
            LanguageScreen(nav)
        }
    }
}
