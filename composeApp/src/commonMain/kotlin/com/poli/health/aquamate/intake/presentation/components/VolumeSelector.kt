package com.poli.health.aquamate.intake.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import com.poli.health.aquamate.ui.theme.dimensions

@Composable
internal fun VolumeSelector(
    volumeMl: Int,
    onIncrementVolume: () -> Unit,
    onDecrementVolume: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = RoundedCornerShape(MaterialTheme.dimensions.RadiusXL)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimensions.SpacingXL),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingM)
        ) {
            VolumeLabel()

            VolumeDisplay(volumeMl = volumeMl)

            VolumeControls(
                onIncrementVolume = onIncrementVolume,
                onDecrementVolume = onDecrementVolume
            )
        }
    }
}

@Composable
private fun VolumeLabel() {
    Text(
        text = AquaMateStrings.Intake.VOLUME_LABEL,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}

@Composable
private fun VolumeDisplay(volumeMl: Int) {
    Text(
        text = "$volumeMl ${AquaMateStrings.Intake.ML_UNIT}",
        style = MaterialTheme.typography.displayLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer
    )
}

@Composable
private fun VolumeControls(
    onIncrementVolume: () -> Unit,
    onDecrementVolume: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.SpacingXL),
        verticalAlignment = Alignment.CenterVertically
    ) {
        VolumeButton(
            onClick = onDecrementVolume,
            icon = Icons.Default.Remove
        )

        VolumeButton(
            onClick = onIncrementVolume,
            icon = Icons.Default.Add
        )
    }
}

@Composable
private fun VolumeButton(
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    FilledIconButton(
        onClick = onClick,
        modifier = Modifier.size(MaterialTheme.dimensions.FabSize),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(MaterialTheme.dimensions.IconSizeL)
        )
    }
}
