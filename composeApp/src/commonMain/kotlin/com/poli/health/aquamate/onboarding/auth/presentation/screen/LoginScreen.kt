package com.poli.health.aquamate.onboarding.auth.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.poli.health.aquamate.onboarding.auth.presentation.model.LoginUiState
import com.poli.health.aquamate.onboarding.auth.presentation.viewmodel.LoginViewModel
import com.poli.health.aquamate.ui.theme.AquaMateDimensions
import com.poli.health.aquamate.ui.theme.AquaMateStrings
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel<LoginViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is LoginUiState.Success -> onLoginSuccess()
            is LoginUiState.Error -> {
                snackbarHostState.showSnackbar(state.message)
                viewModel.clearError()
            }
            else -> Unit
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
                .padding(horizontal = AquaMateDimensions.ScreenMarginHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = AquaMateStrings.Login.TITLE,
                style = MaterialTheme.typography.displayMedium
            )

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingXL))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(AquaMateStrings.Login.EMAIL_LABEL) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is LoginUiState.Loading
            )

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingM))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(AquaMateStrings.Login.PASSWORD_LABEL) },
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is LoginUiState.Loading,
                trailingIcon = {
                    TextButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(
                            text = if (passwordVisible) {
                                AquaMateStrings.Login.HIDE_PASSWORD
                            } else {
                                AquaMateStrings.Login.SHOW_PASSWORD
                            },
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(AquaMateDimensions.SpacingL))

            Button(
                onClick = { viewModel.signIn(email, password) },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is LoginUiState.Loading && email.isNotBlank() && password.isNotBlank()
            ) {
                if (uiState is LoginUiState.Loading) {
                    CircularProgressIndicator()
                } else {
                    Text(AquaMateStrings.Login.SIGN_IN_BUTTON)
                }
            }
        }
    }
}
