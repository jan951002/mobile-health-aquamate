package com.poli.health.aquamate.onboarding.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.draw.clip
import com.poli.health.aquamate.ui.theme.AquaMateDimensions
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateComponents
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.NSDate
import platform.UIKit.UIColor
import platform.UIKit.UIDatePicker
import platform.UIKit.UIDatePickerMode
import platform.UIKit.UIDatePickerStyle

@OptIn(ExperimentalForeignApi::class, ExperimentalTime::class)
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
    var temporaryBirthDate by remember(selectedDate) { mutableStateOf(selectedDate) }

    val dateText = selectedDate?.let {
        "${it.dayOfMonth}/${it.monthNumber}/${it.year}"
    } ?: ""

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = enabled,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                showDatePicker = true
            }
    ) {
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
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (showDatePicker) {
        AlertDialog(
            onDismissRequest = { showDatePicker = false },
            title = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                val surfaceColor = MaterialTheme.colorScheme.surface
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(AquaMateDimensions.DatePickerWheelsHeight)
                        .clip(RoundedCornerShape(AquaMateDimensions.RadiusM))
                        .background(surfaceColor)
                ) {
                    UIKitView(
                        factory = {
                            val picker = UIDatePicker()
                            picker.datePickerMode = UIDatePickerMode.UIDatePickerModeDate
                            picker.preferredDatePickerStyle = UIDatePickerStyle.UIDatePickerStyleWheels
                            picker.backgroundColor = UIColor.colorWithRed(
                                red = surfaceColor.red.toDouble(),
                                green = surfaceColor.green.toDouble(),
                                blue = surfaceColor.blue.toDouble(),
                                alpha = surfaceColor.alpha.toDouble()
                            )
                            picker.setOpaque(true)

                            val calendar = NSCalendar.currentCalendar
                            val now = NSDate()

                            val maxDateComponents = NSDateComponents().apply {
                                year = -18
                            }
                            val maxDate = calendar.dateByAddingComponents(
                                maxDateComponents,
                                toDate = now,
                                options = 0u
                            )
                            maxDate?.let { picker.maximumDate = it }

                            selectedDate?.let { currentBirthDate ->
                                val dateComponents = NSDateComponents().apply {
                                    year = currentBirthDate.year.toLong()
                                    month = currentBirthDate.monthNumber.toLong()
                                    day = currentBirthDate.dayOfMonth.toLong()
                                }
                                calendar.dateFromComponents(dateComponents)?.let { initialDate ->
                                    picker.setDate(initialDate)
                                }
                            }
                            picker
                        },
                        update = { datePicker ->
                            val selectedNSDate = datePicker.date
                            val selectedTimeInterval = selectedNSDate.timeIntervalSince1970
                            val selectedInstant =
                                Instant.fromEpochSeconds(selectedTimeInterval.toLong())
                            val updatedBirthDate =
                                selectedInstant.toLocalDateTime(TimeZone.UTC).date
                            temporaryBirthDate = updatedBirthDate
                        },
                        modifier = Modifier.fillMaxSize(),
                        interactive = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        temporaryBirthDate?.let { finalBirthDate ->
                            onDateSelected(finalBirthDate)
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
        )
    }
}

