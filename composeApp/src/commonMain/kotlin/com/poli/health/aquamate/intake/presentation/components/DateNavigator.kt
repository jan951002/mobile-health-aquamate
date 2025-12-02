package com.poli.health.aquamate.intake.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import com.poli.health.aquamate.ui.theme.dimensions
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
internal fun DateNavigator(
    selectedDate: LocalDate,
    onPreviousDay: () -> Unit,
    onNextDay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val today = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date
    val isToday = selectedDate == today

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.dimensions.ElevationLow
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.dimensions.SpacingM,
                    vertical = MaterialTheme.dimensions.SpacingS
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationButton(
                onClick = onPreviousDay,
                icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = AquaMateStrings.Intake.PREVIOUS_DAY,
                enabled = true
            )

            DateDisplay(selectedDate = selectedDate)

            NavigationButton(
                onClick = onNextDay,
                icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = AquaMateStrings.Intake.NEXT_DAY,
                enabled = !isToday
            )
        }
    }
}

@Composable
private fun NavigationButton(
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    enabled: Boolean
) {
    IconButton(
        onClick = onClick,
        enabled = enabled
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(MaterialTheme.dimensions.IconSizeL),
            tint = if (enabled) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            }
        )
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun DateDisplay(selectedDate: LocalDate) {
    val today = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date

    val displayText = when {
        selectedDate == today -> AquaMateStrings.Intake.TODAY
        selectedDate == today.minus(1, kotlinx.datetime.DateTimeUnit.DAY) ->
            AquaMateStrings.Intake.YESTERDAY

        else -> formatDate(selectedDate)
    }

    Text(
        text = displayText,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

private fun formatDate(date: LocalDate): String {
    val months = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )
    val month = months[date.monthNumber - 1]
    return "$month ${date.dayOfMonth}, ${date.year}"
}
