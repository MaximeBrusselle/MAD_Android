package com.example.musicbrain.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

/**
 * Custom AppBar component for the application.
 *
 * @param currentScreenTitle The title to be displayed on the app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(currentScreenTitle: String) {
    TopAppBar(
        colors =
            topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        title = {
            Text(
                text = currentScreenTitle,
                modifier = Modifier.testTag(currentScreenTitle),
            )
        },
    )
}
