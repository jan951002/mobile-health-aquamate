package com.poli.health.aquamate.intake.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.poli.health.aquamate.intake.domain.model.WaterIntake
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import com.poli.health.aquamate.ui.theme.dimensions

@Composable
internal fun IntakeHistoryItem(
    intake: WaterIntake,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IntakeInfo(intake = intake)

        DeleteButton(onClick = onDeleteClick)
    }
}

@Composable
private fun IntakeInfo(intake: WaterIntake) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingXXS)
    ) {
        VolumeText(volumeMl = intake.volumeMl)

        TimeText(intake = intake)
    }
}

@Composable
private fun VolumeText(volumeMl: Int) {
    Text(
        text = "$volumeMl ${AquaMateStrings.Intake.ML_UNIT}",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun TimeText(intake: WaterIntake) {
    val hour = intake.date.hour.toString().padStart(2, '0')
    val minute = intake.date.minute.toString().padStart(2, '0')
    val timeText = "$hour:$minute"

    Text(
        text = timeText,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun DeleteButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = AquaMateStrings.Intake.DELETE_CONFIRMATION,
            modifier = Modifier.size(MaterialTheme.dimensions.IconSizeS),
            tint = MaterialTheme.colorScheme.error
        )
    }
}

