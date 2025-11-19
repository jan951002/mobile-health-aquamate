package com.poli.health.aquamate.ui.navigation

sealed class Screen(val route: String) {
    object Settings : Screen("settings")
    object Profile : Screen("profile")
    object Language : Screen("language")
}
