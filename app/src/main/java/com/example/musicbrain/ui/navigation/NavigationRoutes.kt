package com.example.musicbrain.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Piano
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.musicbrain.R

enum class NavigationRoutes(
    @StringRes val title: Int,
    val icon: ImageVector,
) {
    Artists(R.string.artistsRoute, Icons.Default.Person),
    Instruments(R.string.genresRoute, Icons.Default.Piano),
}

object Destinations {
    const val ARTISTS = "Artists"
    const val INSTRUMENTS = "Instruments"
    const val ARTIST_DETAIL = "Artists/{artistId}"
    const val INSTRUMENT_DETAIL = "Instruments/{instrumentId}"
}
