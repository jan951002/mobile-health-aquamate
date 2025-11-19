package com.poli.health.aquamate.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import aquamate.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource


@Composable
fun SettingsScreen(nav: NavController) {

    var notificationsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = stringResource(Res.string.settings_title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(Res.string.settings_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        ListItem(
            headlineContent = { Text(stringResource(Res.string.profile)) },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            }
        )

        Divider()

        ListItem(
            headlineContent = { Text(stringResource(Res.string.notifications)) },
            trailingContent = {
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
            }
        )
        Divider()

        ListItem(
            headlineContent = { Text(stringResource(Res.string.language)) },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = null
                )
            }
        )

        Divider()

        ListItem(
            headlineContent = { Text(stringResource(Res.string.logout)) },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = null
                )
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(Res.string.save))
        }
    }
}
