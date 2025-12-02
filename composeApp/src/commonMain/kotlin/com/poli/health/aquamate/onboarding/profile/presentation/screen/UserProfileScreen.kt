package com.poli.health.aquamate.onboarding.profile.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.poli.health.aquamate.onboarding.profile.domain.model.ActivityLevel
import com.poli.health.aquamate.onboarding.profile.domain.model.Gender
import com.poli.health.aquamate.onboarding.profile.presentation.components.ProfileDateField
import com.poli.health.aquamate.onboarding.profile.presentation.components.ProfileSectionHeader
import com.poli.health.aquamate.onboarding.profile.presentation.components.ProfileTextField
import com.poli.health.aquamate.onboarding.profile.presentation.model.UserProfileEvent
import com.poli.health.aquamate.onboarding.profile.presentation.viewmodel.UserProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserProfileScreen(
    onProfileSaved: () -> Unit,
    viewModel: UserProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    // FocusRequester for height field
    val heightFocusRequester = remember { FocusRequester() }

    LaunchedEffect(state.isSaveSuccessful) {
        if (state.isSaveSuccessful) {
            onProfileSaved()
        }
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Section
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tell us about yourself",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "We'll use this information to personalize your daily hydration goal",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Body Measurements Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Weight and Height Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ProfileTextField(
                            value = state.weight,
                            onValueChange = { viewModel.onEvent(UserProfileEvent.OnWeightChanged(it)) },
                            label = "Weight (kg)",
                            leadingIcon = Icons.Filled.FitnessCenter,
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next,
                            keyboardActions = KeyboardActions(
                                onNext = { heightFocusRequester.requestFocus() }
                            ),
                            enabled = !state.isLoading,
                            modifier = Modifier.weight(1f)
                        )

                        ProfileTextField(
                            value = state.height,
                            onValueChange = { viewModel.onEvent(UserProfileEvent.OnHeightChanged(it)) },
                            label = "Height (cm)",
                            leadingIcon = Icons.Filled.Height,
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done,
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() }
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(heightFocusRequester),
                            enabled = !state.isLoading
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Date of Birth
                    ProfileDateField(
                        selectedDate = state.birthDate,
                        onDateSelected = { viewModel.onEvent(UserProfileEvent.OnBirthDateSelected(it)) },
                        label = "Date of Birth",
                        leadingIcon = Icons.Filled.Cake,
                        enabled = !state.isLoading
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Gender Section
            ProfileSectionHeader(
                title = "Gender",
                icon = Icons.Filled.Wc,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Gender.entries.forEach { gender ->
                    GenderCard(
                        gender = gender,
                        selected = state.gender == gender,
                        enabled = !state.isLoading,
                        onSelect = { viewModel.onEvent(UserProfileEvent.OnGenderSelected(gender)) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Activity Level Section
            ProfileSectionHeader(
                title = "Activity Level",
                icon = Icons.Filled.DirectionsRun,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ActivityLevel.entries.forEach { level ->
                    ActivityLevelCard(
                        level = level,
                        selected = state.activityLevel == level,
                        enabled = !state.isLoading,
                        onSelect = { viewModel.onEvent(UserProfileEvent.OnActivityLevelSelected(level)) }
                    )
                }
            }

            // Preview Card
            if (state.dailyWaterGoalPreview > 0) {
                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Your Daily Goals",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            GoalMetric(
                                label = "Water Goal",
                                value = "${state.dailyWaterGoalPreview} ml"
                            )

                            GoalMetric(
                                label = "BMI",
                                value = state.bmiPreview.toString().take(4)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Save Button
            Button(
                onClick = { viewModel.onEvent(UserProfileEvent.OnSaveProfile) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        "Save Profile",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun GenderCard(
    gender: Gender,
    selected: Boolean,
    enabled: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        onClick = onSelect,
        modifier = modifier.height(100.dp),
        enabled = enabled,
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = when (gender) {
                    Gender.MALE -> Icons.Filled.Male
                    Gender.FEMALE -> Icons.Filled.Female
                },
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = when (gender) {
                    Gender.MALE -> "Male"
                    Gender.FEMALE -> "Female"
                },
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (selected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

@Composable
private fun ActivityLevelCard(
    level: ActivityLevel,
    selected: Boolean,
    enabled: Boolean,
    onSelect: () -> Unit
) {
    OutlinedCard(
        onClick = onSelect,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = if (selected) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = null,
                enabled = enabled
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = when (level) {
                        ActivityLevel.SEDENTARY -> "Sedentary"
                        ActivityLevel.MODERATE -> "Moderate"
                        ActivityLevel.INTENSE -> "Intense"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = when (level) {
                        ActivityLevel.SEDENTARY -> "Little to no exercise"
                        ActivityLevel.MODERATE -> "Exercise 3-5 days/week"
                        ActivityLevel.INTENSE -> "Intense exercise 6-7 days/week"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun GoalMetric(
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
