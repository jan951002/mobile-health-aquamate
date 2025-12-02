package com.poli.health.aquamate.onboarding.profile.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIDatePicker
import platform.UIKit.UIDatePickerMode

@OptIn(ExperimentalForeignApi::class)
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
    var tempSelectedDate by remember(selectedDate) { mutableStateOf(selectedDate) }

    val dateText = selectedDate?.let {
        "${it.dayOfMonth}/${it.monthNumber}/${it.year}"
    } ?: ""

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
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) { showDatePicker = true }
    )

    if (showDatePicker) {
        AlertDialog(
            onDismissRequest = { showDatePicker = false },
            title = { Text(label) },
            text = {
                Column {
                    UIKitView(
                        factory = {
                            UIDatePicker().apply {
                                datePickerMode = UIDatePickerMode.UIDatePickerModeDate
                                preferredDatePickerStyle = platform.UIKit.UIDatePickerStyleWheels

                                // Set initial date
                                selectedDate?.let { date ->
                                    val components = NSDateComponents().apply {
                                        year = date.year.toLong()
                                        month = date.monthNumber.toLong()
                                        day = date.dayOfMonth.toLong()
                                    }
                                    NSCalendar.currentCalendar.dateFromComponents(components)?.let {
                                        setDate(it)
                                    }
                                }

                                addTarget(
                                    target = this,
                                    action = platform.objc.sel_registerName("dateChanged"),
                                    forControlEvents = platform.UIKit.UIControlEventValueChanged
                                )
                            }
                        },
                        update = { picker ->
                            val nsDate = picker.date
                            val timeInterval = nsDate.timeIntervalSince1970
                            val instant = Instant.fromEpochSeconds(timeInterval.toLong())
                            val date = instant.toLocalDateTime(TimeZone.UTC).date
                            tempSelectedDate = date
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        tempSelectedDate?.let { onDateSelected(it) }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
