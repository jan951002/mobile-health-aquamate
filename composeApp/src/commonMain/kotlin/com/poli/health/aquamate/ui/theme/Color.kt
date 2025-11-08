package com.poli.health.aquamate.ui.theme

import androidx.compose.ui.graphics.Color

val LightPrimary = Color(0xFF1E88E5)
val LightPrimaryVariant = Color(0xFF1565C0)
val LightPrimaryLight = Color(0xFF64B5F6)
val LightOnPrimary = Color(0xFFFFFFFF)

val LightSecondary = Color(0xFF26C6DA)
val LightSecondaryVariant = Color(0xFF00ACC1)
val LightOnSecondary = Color(0xFF000000)

val LightSuccess = Color(0xFF66BB6A)
val LightSuccessLight = Color(0xFFA5D6A7)
val LightWarning = Color(0xFFFFA726)
val LightWarningLight = Color(0xFFFFB74D)
val LightError = Color(0xFFEF5350)
val LightErrorLight = Color(0xFFE57373)

val LightBackground = Color(0xFFFAFAFA)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceVariant = Color(0xFFF5F5F5)
val LightOnBackground = Color(0xFF212121)
val LightOnSurface = Color(0xFF212121)

val LightOnBackgroundSecondary = Color(0xFF757575)
val LightOnBackgroundTertiary = Color(0xFFBDBDBD)

val LightDivider = Color(0xFFE0E0E0)
val LightInfo = Color(0xFF42A5F5)

val DarkPrimary = Color(0xFF64B5F6)
val DarkPrimaryVariant = Color(0xFF90CAF9)
val DarkPrimaryDark = Color(0xFF1976D2)
val DarkOnPrimary = Color(0xFF000000)

val DarkSecondary = Color(0xFF4DD0E1)
val DarkSecondaryVariant = Color(0xFF26C6DA)
val DarkOnSecondary = Color(0xFF000000)

val DarkSuccess = Color(0xFF81C784)
val DarkSuccessDark = Color(0xFF66BB6A)
val DarkWarning = Color(0xFFFFB74D)
val DarkWarningDark = Color(0xFFFFA726)
val DarkError = Color(0xFFE57373)
val DarkErrorDark = Color(0xFFEF5350)

val DarkBackground = Color(0xFF121212)
val DarkSurface = Color(0xFF1E1E1E)
val DarkSurfaceElevated1 = Color(0xFF2C2C2C)
val DarkSurfaceElevated2 = Color(0xFF383838)
val DarkSurfaceVariant = Color(0xFF252525)
val DarkOnBackground = Color(0xFFFFFFFF)
val DarkOnSurface = Color(0xFFFFFFFF)

val DarkOnBackgroundSecondary = Color(0xFFB3B3B3)
val DarkOnBackgroundTertiary = Color(0xFF808080)

val DarkDivider = Color(0xFF404040)
val DarkInfo = Color(0xFF64B5F6)

val LightWaterGradientStart = Color(0xFF64B5F6)
val LightWaterGradientEnd = Color(0xFF1E88E5)
val LightSuccessGradientStart = Color(0xFFA5D6A7)
val LightSuccessGradientEnd = Color(0xFF66BB6A)

val DarkWaterGradientStart = Color(0xFF90CAF9)
val DarkWaterGradientEnd = Color(0xFF64B5F6)
val DarkSuccessGradientStart = Color(0xFF81C784)
val DarkSuccessGradientEnd = Color(0xFF66BB6A)

val LightProgressBackground = Color(0xFFE3F2FD)
val DarkProgressBackground = Color(0xFF1E3A5F)

val LightQuickButtonPressed = Color(0xFFE3F2FD)
val DarkQuickButtonPressed = Color(0xFF1E3A5F)

fun Color.withHighEmphasis() = this.copy(alpha = 0.87f)
fun Color.withMediumEmphasis() = this.copy(alpha = 0.60f)
fun Color.withDisabledEmphasis() = this.copy(alpha = 0.38f)

object TextOpacity {
    const val HIGH_EMPHASIS = 0.87f
    const val MEDIUM_EMPHASIS = 0.60f
    const val DISABLED_EMPHASIS = 0.38f
}

object OverlayOpacity {
    const val SCRIM_LIGHT = 0.32f
    const val SCRIM_DARK = 0.48f
    const val HOVER_LIGHT = 0.08f
    const val HOVER_DARK = 0.08f
    const val PRESSED_LIGHT = 0.16f
    const val PRESSED_DARK = 0.16f
}
