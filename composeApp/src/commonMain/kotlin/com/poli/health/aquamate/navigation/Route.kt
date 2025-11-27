package com.poli.health.aquamate.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Auth : Route

    @Serializable
    data object Intake : Route
}
