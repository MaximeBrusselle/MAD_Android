package com.example.musicbrain.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.musicbrain.R

enum class NavigationRoutes(@StringRes val title: Int, val icon: ImageVector) {
    Artists(R.string.artistsRoute, Icons.Default.Person),
    Genres(R.string.genresRoute, Icons.Default.LibraryMusic),
}