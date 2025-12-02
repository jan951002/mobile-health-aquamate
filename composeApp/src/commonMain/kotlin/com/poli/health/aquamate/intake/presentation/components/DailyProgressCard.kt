package com.poli.health.aquamate.intake.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import com.poli.health.aquamate.ui.theme.dimensions
import com.poli.health.aquamate.ui.theme.extendedColors

@Composable
internal fun DailyProgressCard(
    currentMl: Int,
    goalMl: Int,
    progressPercentage: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.dimensions.ElevationMedium
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimensions.CardPadding),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingM)
        ) {
            ProgressHeader(
                currentMl = currentMl,
                goalMl = goalMl
            )

            ProgressBar(
                progressPercentage = progressPercentage
            )

            MotivationalMessage(
                progressPercentage = progressPercentage
            )
        }
    }
}

@Composable
private fun ProgressHeader(
    currentMl: Int,
    goalMl: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AquaMateStrings.Intake.DAILY_PROGRESS,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "$currentMl ${AquaMateStrings.Intake.PROGRESS_OF} $goalMl ${AquaMateStrings.Intake.ML_UNIT}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ProgressBar(
    progressPercentage: Int
) {
    val progress = (progressPercentage / 100f).coerceIn(0f, 1f)

    LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.extendedColors.progressBackground
    )
}

@Composable
private fun MotivationalMessage(
    progressPercentage: Int
) {
    val message = when {
        progressPercentage >= 100 -> AquaMateStrings.Intake.GOAL_ACHIEVED
        progressPercentage >= 75 -> AquaMateStrings.Intake.ALMOST_THERE
        progressPercentage >= 50 -> AquaMateStrings.Intake.KEEP_GOING
        else -> AquaMateStrings.Intake.GREAT_START
    }

    val color = when {
        progressPercentage >= 100 -> MaterialTheme.extendedColors.success
        else -> MaterialTheme.colorScheme.primary
    }

    Text(
        text = message,
        style = MaterialTheme.typography.labelLarge,
        color = color
    )
}
