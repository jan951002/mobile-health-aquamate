package com.poli.health.aquamate.onboarding.profile.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import kotlinx.datetime.LocalDate
import java.time.Instant
import java.time.LocalDate as JavaLocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun ProfileDateField(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    modifier: Modifier,
    enabled: Boolean
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val dateText = selectedDate?.let {
        "${it.dayOfMonth}/${it.monthNumber}/${it.year}"
    } ?: ""

    val isPressed by interactionSource.collectIsPressedAsState()
    
    LaunchedEffect(isPressed) {
        if (isPressed && enabled) {
            showDatePicker = true
        }
    }

    OutlinedTextField(
        value = dateText,
        onValueChange = { },
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = "$label icon",
                tint = if (enabled) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                }
            )
        },
        readOnly = true,
        enabled = enabled,
        interactionSource = interactionSource,
        modifier = modifier.fillMaxWidth()
    )

    if (showDatePicker) {
        val initialSelectedDateMillis = selectedDate?.let { birthDate ->
            val selectedBirthDate = JavaLocalDate.of(birthDate.year, birthDate.monthNumber, birthDate.dayOfMonth)
            selectedBirthDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        }
        
        val maxAllowedDate = JavaLocalDate.now().minusYears(18)
        val maxAllowedDateMillis = maxAllowedDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = initialSelectedDateMillis,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis <= maxAllowedDateMillis
                }
            }
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { selectedMillis ->
                            val selectedInstant = Instant.ofEpochMilli(selectedMillis)
                            val selectedBirthDate = selectedInstant.atZone(ZoneOffset.UTC).toLocalDate()
                            val userBirthDate = LocalDate(
                                selectedBirthDate.year,
                                selectedBirthDate.monthValue,
                                selectedBirthDate.dayOfMonth
                            )
                            onDateSelected(userBirthDate)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text(
                        text = AquaMateStrings.Common.OK,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(
                        text = AquaMateStrings.Common.CANCEL,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                title = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    )
                }
            )
        }
    }
}
