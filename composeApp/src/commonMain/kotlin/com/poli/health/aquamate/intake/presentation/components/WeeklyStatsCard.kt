package com.poli.health.aquamate.intake.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.poli.health.aquamate.intake.domain.model.WeeklyStats
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import com.poli.health.aquamate.ui.theme.dimensions

@Composable
internal fun WeeklyStatsCard(
    weeklyStats: WeeklyStats?,
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
            StatsHeader()

            if (weeklyStats != null) {
                StatsContent(weeklyStats = weeklyStats)
            } else {
                NoStatsMessage()
            }
        }
    }
}

@Composable
private fun StatsHeader() {
    Text(
        text = AquaMateStrings.Intake.WEEKLY_STATS_TITLE,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun StatsContent(weeklyStats: WeeklyStats) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingS)
    ) {
        AverageRow(averageMl = weeklyStats.averageMl)

        GoalAchievedRow(
            daysAchieved = weeklyStats.daysWithGoalAchieved,
            totalDays = weeklyStats.totalDays
        )
    }
}

@Composable
private fun AverageRow(averageMl: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AquaMateStrings.Intake.WEEKLY_AVERAGE,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "$averageMl ${AquaMateStrings.Intake.ML_UNIT}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun GoalAchievedRow(
    daysAchieved: Int,
    totalDays: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AquaMateStrings.Intake.DAYS_ACHIEVED,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "$daysAchieved ${AquaMateStrings.Intake.DAYS_OF} $totalDays ${AquaMateStrings.Intake.DAYS_UNIT}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun NoStatsMessage() {
    Text(
        text = AquaMateStrings.Common.LOADING,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(MaterialTheme.dimensions.SpacingM)
    )
}
