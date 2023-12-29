package com.example.musicbrain.ui.components

import android.util.Log
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
import androidx.compose.ui.res.stringResource
import com.example.musicbrain.R
import com.example.musicbrain.ui.navigation.NavigationRoutes

@Composable
fun BottomBar(goToArtists: () -> Unit, goToGenres: () -> Unit) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar (
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        NavigationBarItem(
            selected = selectedItemIndex == 0,
            onClick = {
                Log.i("BottomBar", "selectedItemIndex: $selectedItemIndex")
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
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.background,
            )
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
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.background,
            )
        )
    }
}