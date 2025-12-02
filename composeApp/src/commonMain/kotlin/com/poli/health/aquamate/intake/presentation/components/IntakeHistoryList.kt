package com.poli.health.aquamate.intake.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    Column(
        modifier = modifier.fillMaxWidth()
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

@Composable
private fun HistoryHeader() {
    Text(
        text = AquaMateStrings.Intake.HISTORY_TITLE,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(bottom = MaterialTheme.dimensions.SpacingM)
    )
}

@Composable
private fun EmptyHistoryMessage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimensions.SpacingXXL),
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
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.ListItemSpacing),
        contentPadding = PaddingValues(vertical = MaterialTheme.dimensions.SpacingXS)
    ) {
        items(
            items = intakes,
            key = { it.id }
        ) { intake ->
            IntakeHistoryItem(
                intake = intake,
                onDeleteClick = { onDeleteIntake(intake.id) }
            )
        }
    }
}
