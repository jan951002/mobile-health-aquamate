package com.poli.health.aquamate.intake.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.poli.health.aquamate.intake.domain.model.WaterIntake
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import com.poli.health.aquamate.ui.theme.dimensions

@Composable
internal fun IntakeHistoryList(
    intakes: List<WaterIntake>,
    onDeleteIntake: (String) -> Unit,
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
                .padding(MaterialTheme.dimensions.CardPadding)
        ) {
            HistoryHeader()

            if (intakes.isEmpty()) {
                EmptyHistoryMessage()
            } else {
                HistoryItems(
                    intakes = intakes,
                    onDeleteIntake = onDeleteIntake
                )
            }
        }
    }
}

@Composable
private fun HistoryHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingS)
    ) {
        Icon(
            imageVector = Icons.Default.WaterDrop,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(MaterialTheme.dimensions.IconSizeM)
        )
        Text(
            text = AquaMateStrings.Intake.HISTORY_TITLE,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun EmptyHistoryMessage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = MaterialTheme.dimensions.SpacingXL,
                horizontal = MaterialTheme.dimensions.SpacingM
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = AquaMateStrings.Intake.HISTORY_EMPTY,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun HistoryItems(
    intakes: List<WaterIntake>,
    onDeleteIntake: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(top = MaterialTheme.dimensions.SpacingM),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.ListItemSpacing)
    ) {
        intakes.forEach { intake ->
            IntakeHistoryItem(
                intake = intake,
                onDeleteClick = { onDeleteIntake(intake.id) }
            )
        }
    }
}
