package com.example.musicbrain.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicbrain.ui.artistDetailScreen.ArtistDetailScreen
import com.example.musicbrain.ui.artistsScreen.ArtistsScreen
import com.example.musicbrain.ui.instrumentDetailScreen.InstrumentDetailScreen
import com.example.musicbrain.ui.instrumentsScreen.InstrumentsScreen

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
                    navController.navigate("Artists/$artistId")
                }
            )
        }

        composable(Destinations.ARTIST_DETAIL) { backStackEntry ->
            ArtistDetailScreen(
                artistId = backStackEntry.arguments?.getString("artistId")!!,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Destinations.INSTRUMENTS) {
            InstrumentsScreen(
                toDetailPage = { instrumentId ->
                    navController.navigate("Instruments/$instrumentId")
                }
            )
        }
        composable(Destinations.INSTRUMENT_DETAIL) { backStackEntry ->
            InstrumentDetailScreen(
                instrumentId = backStackEntry.arguments?.getString("instrumentId")!!,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
