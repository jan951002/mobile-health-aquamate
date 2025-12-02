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
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.poli.health.aquamate.commons.presentation.components.AquaMateButton
import com.poli.health.aquamate.onboarding.profile.domain.model.ActivityLevel
import com.poli.health.aquamate.ui.theme.AquaMateDimensions
import com.poli.health.aquamate.onboarding.profile.domain.model.Gender
import com.poli.health.aquamate.onboarding.profile.presentation.components.ProfileDateField
import com.poli.health.aquamate.onboarding.profile.presentation.components.ProfileSectionHeader
import com.poli.health.aquamate.onboarding.profile.presentation.components.ProfileTextField
import com.poli.health.aquamate.onboarding.profile.presentation.model.UserProfileEvent
import com.poli.health.aquamate.onboarding.profile.presentation.viewmodel.UserProfileViewModel
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserProfileScreen(
    onProfileSaved: () -> Unit,
    viewModel: UserProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
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
                .padding(horizontal = AquaMateDimensions.SpacingL, vertical = AquaMateDimensions.SpacingM)
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingS))

            Text(
                text = AquaMateStrings.Profile.TITLE,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingS))

            Text(
                text = AquaMateStrings.Profile.SUBTITLE,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = AquaMateDimensions.SpacingM)
            )

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingXL))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = AquaMateDimensions.ElevationNone)
            ) {
                Column(
                    modifier = Modifier.padding(AquaMateDimensions.CardPadding)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(AquaMateDimensions.SpacingM)
                    ) {
                        ProfileTextField(
                            value = state.weight,
                            onValueChange = { viewModel.onEvent(UserProfileEvent.OnWeightChanged(it)) },
                            label = AquaMateStrings.Profile.WEIGHT_LABEL,
                            leadingIcon = Icons.Filled.FitnessCenter,
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Next,
                            keyboardActions = KeyboardActions(
                                onNext = { heightFocusRequester.requestFocus() }
                            ),
                            enabled = !state.isLoading,
                            modifier = Modifier.fillMaxWidth()
                        )

                        ProfileTextField(
                            value = state.height,
                            onValueChange = { viewModel.onEvent(UserProfileEvent.OnHeightChanged(it)) },
                            label = AquaMateStrings.Profile.HEIGHT_LABEL,
                            leadingIcon = Icons.Filled.Height,
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done,
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() }
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(heightFocusRequester),
                            enabled = !state.isLoading
                        )
                    }

                    Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingM))

                    ProfileDateField(
                        selectedDate = state.birthDate,
                        onDateSelected = { viewModel.onEvent(UserProfileEvent.OnBirthDateSelected(it)) },
                        label = AquaMateStrings.Profile.DATE_OF_BIRTH_LABEL,
                        leadingIcon = Icons.Filled.Cake,
                        enabled = !state.isLoading
                    )
                }
            }

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingL))

            ProfileSectionHeader(
                title = AquaMateStrings.Profile.GENDER_TITLE,
                icon = Icons.Filled.Wc,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingMS))

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

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingL))
            ProfileSectionHeader(
                title = AquaMateStrings.Profile.ACTIVITY_LEVEL_TITLE,
                icon = Icons.AutoMirrored.Filled.DirectionsRun,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingMS))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(AquaMateDimensions.SpacingS)
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

            if (state.dailyWaterGoalPreview > 0) {
                Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingL))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = AquaMateDimensions.ElevationLow)
                ) {
                    Column(
                        modifier = Modifier.padding(AquaMateDimensions.CardPadding)
                    ) {
                        Text(
                            text = AquaMateStrings.Profile.DAILY_GOALS_TITLE,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingM))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            GoalMetric(
                                label = AquaMateStrings.Profile.WATER_GOAL_LABEL,
                                value = "${state.dailyWaterGoalPreview} ml"
                            )

                            GoalMetric(
                                label = AquaMateStrings.Profile.BMI_LABEL,
                                value = state.bmiPreview.toString().take(4)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingXL))

            AquaMateButton(
                text = AquaMateStrings.Profile.SAVE_PROFILE_BUTTON,
                onClick = { viewModel.onEvent(UserProfileEvent.OnSaveProfile) },
                modifier = Modifier.height(AquaMateDimensions.FabSize),
                enabled = !state.isLoading,
                isLoading = state.isLoading,
                leadingIcon = Icons.Filled.Save
            )

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingL))
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
        modifier = modifier.height(AquaMateDimensions.StatCardMinHeight),
        enabled = enabled,
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = BorderStroke(
            width = if (selected) AquaMateDimensions.BorderWidthThick else AquaMateDimensions.BorderWidthThin,
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
                .padding(AquaMateDimensions.CardPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = when (gender) {
                    Gender.MALE -> Icons.Filled.Male
                    Gender.FEMALE -> Icons.Filled.Female
                },
                contentDescription = null,
                modifier = Modifier.size(AquaMateDimensions.IconSizeL),
                tint = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingS))

            Text(
                text = when (gender) {
                    Gender.MALE -> AquaMateStrings.Profile.MALE
                    Gender.FEMALE -> AquaMateStrings.Profile.FEMALE
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
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = BorderStroke(
            width = if (selected) AquaMateDimensions.BorderWidthThick else AquaMateDimensions.BorderWidthThin,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AquaMateDimensions.CardPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = null,
                enabled = enabled
            )

            Spacer(modifier = Modifier.width(AquaMateDimensions.SpacingMS))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = when (level) {
                        ActivityLevel.SEDENTARY -> AquaMateStrings.Profile.SEDENTARY
                        ActivityLevel.MODERATE -> AquaMateStrings.Profile.MODERATE
                        ActivityLevel.INTENSE -> AquaMateStrings.Profile.INTENSE
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                    color = if (selected) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )

                Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingXS))

                Text(
                    text = when (level) {
                        ActivityLevel.SEDENTARY -> AquaMateStrings.Profile.SEDENTARY_DESCRIPTION
                        ActivityLevel.MODERATE -> AquaMateStrings.Profile.MODERATE_DESCRIPTION
                        ActivityLevel.INTENSE -> AquaMateStrings.Profile.INTENSE_DESCRIPTION
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = if (selected) {
                        MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
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
            color = MaterialTheme.colorScheme.onSurfaceVariant
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
