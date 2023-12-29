package com.example.musicbrain.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.musicbrain.R
import com.example.musicbrain.ui.navigation.NavigationRoutes

@Composable
fun BottomBar(goToArtists: () -> Unit, goToGenres: () -> Unit) {
    var selectedItemIndex by remember { mutableIntStateOf(0) }

    NavigationBar (
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
                    contentDescription = R.string.artistsRoute.toString(),
                )
            },
            label = {
                Text(text = stringResource(id = R.string.artistsRoute))
            }
        )
        NavigationBarItem(
            selected = selectedItemIndex == 1,
            onClick = {
                goToGenres()
                selectedItemIndex = 1
            },
            icon = {
                Icon(
                    imageVector = NavigationRoutes.Genres.icon,
                    contentDescription = R.string.genresRoute.toString(),
                )
            },
            label = {
                Text(text = stringResource(id = R.string.genresRoute))
            }
        )
    }
}