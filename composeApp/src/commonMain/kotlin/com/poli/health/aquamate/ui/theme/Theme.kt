package com.poli.health.aquamate.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAquaMateExtendedColors = staticCompositionLocalOf {
    AquaMateLightExtendedColors
}

val LocalAquaMateDimensions = staticCompositionLocalOf {
    AquaMateDimensions
}

@Composable
fun AquaMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        AquaMateDarkColorScheme
    } else {
        AquaMateLightColorScheme
    }
    
    val extendedColors = if (darkTheme) {
        AquaMateDarkExtendedColors
    } else {
        AquaMateLightExtendedColors
    }
    
    val typography = AquaMateTypography.copy(
        displayLarge = AquaMateTypography.displayLarge.copy(color = colorScheme.onBackground),
        displayMedium = AquaMateTypography.displayMedium.copy(color = colorScheme.onBackground),
        displaySmall = AquaMateTypography.displaySmall.copy(color = colorScheme.onBackground),
        headlineLarge = AquaMateTypography.headlineLarge.copy(color = colorScheme.onBackground),
        bodyLarge = AquaMateTypography.bodyLarge.copy(color = colorScheme.onBackground),
        bodyMedium = AquaMateTypography.bodyMedium.copy(color = colorScheme.onBackground),
        bodySmall = AquaMateTypography.bodySmall.copy(color = colorScheme.onSurfaceVariant),
        labelLarge = AquaMateTypography.labelLarge.copy(color = colorScheme.onPrimary),
        labelMedium = AquaMateTypography.labelMedium.copy(color = colorScheme.onPrimary),
        labelSmall = AquaMateTypography.labelSmall.copy(color = colorScheme.onSurfaceVariant),
        titleLarge = AquaMateTypography.titleLarge.copy(color = colorScheme.onBackground),
        titleMedium = AquaMateTypography.titleMedium.copy(color = colorScheme.onBackground),
        titleSmall = AquaMateTypography.titleSmall.copy(color = colorScheme.onSurfaceVariant)
    )
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = {
            CompositionLocalProvider(
                LocalAquaMateExtendedColors provides extendedColors,
                LocalAquaMateDimensions provides AquaMateDimensions,
                content = content
            )
        }
    )
}

object AquaMateTheme {
    val extendedColors: AquaMateExtendedColors
        @Composable
        get() = LocalAquaMateExtendedColors.current
    
    val dimensions: AquaMateDimensions
        @Composable
        get() = LocalAquaMateDimensions.current
    
    object TextStyles {
        val numberLarge @Composable get() = AquaMateTextStyles.NumberLarge.copy(
            color = MaterialTheme.colorScheme.primary
        )
        val numberMedium @Composable get() = AquaMateTextStyles.NumberMedium.copy(
            color = MaterialTheme.colorScheme.primary
        )
        val numberSmall @Composable get() = AquaMateTextStyles.NumberSmall.copy(
            color = MaterialTheme.colorScheme.onSurface
        )
        val caption @Composable get() = AquaMateTextStyles.Caption.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        val overline @Composable get() = AquaMateTextStyles.Overline.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        val quickButtonText @Composable get() = AquaMateTextStyles.QuickButtonText.copy(
            color = MaterialTheme.colorScheme.onSurface
        )
        val progressText @Composable get() = AquaMateTextStyles.ProgressText.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        val statCardTitle @Composable get() = AquaMateTextStyles.StatCardTitle.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        val statCardValue @Composable get() = AquaMateTextStyles.StatCardValue.copy(
            color = MaterialTheme.colorScheme.primary
        )
        val navigationText @Composable get() = AquaMateTextStyles.NavigationText.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

val MaterialTheme.extendedColors: AquaMateExtendedColors
    @Composable
    get() = LocalAquaMateExtendedColors.current

val MaterialTheme.dimensions: AquaMateDimensions
    @Composable
    get() = LocalAquaMateDimensions.current
