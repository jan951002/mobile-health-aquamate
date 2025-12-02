package com.poli.health.aquamate.intake.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import com.poli.health.aquamate.ui.theme.dimensions

@Composable
internal fun VolumeSelector(
    volumeMl: Int,
    onIncrementVolume: () -> Unit,
    onDecrementVolume: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        VolumeButton(
            onClick = onDecrementVolume,
            icon = Icons.Default.Remove
        )

        VolumeDisplay(volumeMl = volumeMl)

        VolumeButton(
            onClick = onIncrementVolume,
            icon = Icons.Default.Add
        )
    }
}

@Composable
private fun VolumeDisplay(volumeMl: Int) {
    Text(
        text = "$volumeMl ${AquaMateStrings.Intake.ML_UNIT}",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun VolumeButton(
    onClick: () -> Unit,
    icon: ImageVector
) {
    FilledIconButton(
        onClick = onClick,
        modifier = Modifier.size(MaterialTheme.dimensions.FabSizeSmall),
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(MaterialTheme.dimensions.IconSizeS)
        )
    }
}
