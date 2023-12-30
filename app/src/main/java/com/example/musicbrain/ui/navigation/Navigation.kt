package com.example.musicbrain.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.musicbrain.ui.artistDetailScreen.ArtistDetailScreen
import com.example.musicbrain.ui.artistScreen.ArtistsScreen
import com.example.musicbrain.ui.genresScreen.GenresScreen

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.ARTISTS,
        modifier = modifier
    ) {
        composable(Destinations.ARTISTS) {
            ArtistsScreen(
                toDetailPage = { artistId ->
                    Log.d("ArtistDetail", "toDetailPage: Detail/$artistId")
                    navController.navigate("Detail/$artistId")
                }
            )
        }

        composable(Destinations.DETAIL) { backStackEntry ->
            ArtistDetailScreen(
                artistId = backStackEntry.arguments?.getString("artistId")!!,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Destinations.GENRES) {
            GenresScreen()
        }
    }
}
