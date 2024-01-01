package com.example.musicbrain.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.musicbrain.R
import com.example.musicbrain.ui.navigation.NavigationRoutes

/**
 * Custom Bottom Bar component for navigation within the application.
 *
 * @param goToArtists Callback to navigate to the Artists screen.
 * @param goToInstruments Callback to navigate to the Genres screen.
 */
@Composable
fun BottomBar(
    goToArtists: () -> Unit,
    goToInstruments: () -> Unit,
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        NavigationBarItem(
            selected = selectedItemIndex == 0,
            onClick = {
                goToArtists()
                selectedItemIndex = 0
            },
            icon = {
                Icon(
                    imageVector = NavigationRoutes.Artists.icon,
                    contentDescription = "Navigate to ${NavigationRoutes.Artists.title}",
                )
            },
            label = {
                Text(text = stringResource(id = R.string.artistsRoute))
            },
            colors =
                NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.background,
                ),
            modifier = Modifier.testTag("NavigateToArtistsBottom"),
        )

        NavigationBarItem(
            selected = selectedItemIndex == 1,
            onClick = {
                goToInstruments()
                selectedItemIndex = 1
            },
            icon = {
                Icon(
                    imageVector = NavigationRoutes.Instruments.icon,
                    contentDescription = "Navigate to ${NavigationRoutes.Instruments.title}",
                )
            },
            label = {
                Text(text = stringResource(id = R.string.instrumentsRoute))
            },
            colors =
                NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.background,
                ),
            modifier = Modifier.testTag("NavigateToInstrumentsBottom"),
        )
    }
}
