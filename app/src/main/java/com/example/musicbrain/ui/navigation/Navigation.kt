package com.example.musicbrain.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicbrain.ui.artistScreen.ArtistsScreen
import com.example.musicbrain.ui.genresScreen.GenresScreen

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Artists.name,
        modifier = modifier
    ) {
        composable(NavigationRoutes.Artists.name) {
            ArtistsScreen()
        }
        composable(NavigationRoutes.Genres.name) {
            GenresScreen()
        }
    }
}