package com.example.musicbrain.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicbrain.R
import com.example.musicbrain.ui.components.AppBar
import com.example.musicbrain.ui.components.BottomBar
import com.example.musicbrain.ui.components.NavigationDrawerContent
import com.example.musicbrain.ui.components.NavigationRail
import com.example.musicbrain.ui.navigation.Destinations
import com.example.musicbrain.ui.navigation.Navigation
import com.example.musicbrain.ui.navigation.NavigationRoutes
import com.example.musicbrain.ui.util.MusicBrainNavigationType

@Composable
fun MusicBrain(
    navigationType: MusicBrainNavigationType,
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val goToArtists: () -> Unit = {
        navController.popBackStack(
            NavigationRoutes.Artists.name,
            inclusive = false,
        )
    }

    val goToGenres = { navController.navigate(Destinations.INSTRUMENTS) { launchSingleTop = true } }
    val currentScreenTitle =
        backStackEntry?.destination?.route?.let {
            when (it) {
                Destinations.ARTISTS -> stringResource(id = R.string.artistsRoute)
                Destinations.INSTRUMENTS -> stringResource(id = R.string.instrumentsRoute)
                Destinations.ARTIST_DETAIL -> stringResource(id = R.string.artistDetailRoute)
                Destinations.INSTRUMENT_DETAIL -> stringResource(id = R.string.instrumentDetailRoute)
                else -> stringResource(id = R.string.app_name)
            }
        } ?: stringResource(id = R.string.app_name)

    when (navigationType) {
        MusicBrainNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            PermanentNavigationDrawer(drawerContent = {
                PermanentDrawerSheet(Modifier.width(dimensionResource(R.dimen.drawer_width))) {
                    NavigationDrawerContent(
                        selectedDestination = navController.currentDestination,
                        onTabPressed = { node: String -> navController.navigate(node) },
                        modifier =
                            Modifier
                                .wrapContentWidth()
                                .fillMaxHeight()
                                .background(MaterialTheme.colorScheme.inverseOnSurface)
                                .padding(dimensionResource(R.dimen.drawer_padding_content)),
                    )
                }
            }) {
                Scaffold(
                    containerColor = Color.Transparent,
                    topBar = {
                        AppBar(
                            currentScreenTitle = currentScreenTitle,
                        )
                    },
                ) { innerPadding ->

                    Navigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
        MusicBrainNavigationType.BOTTOM_NAVIGATION -> {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    AppBar(
                        currentScreenTitle = currentScreenTitle,
                    )
                },
                bottomBar = {
                    BottomBar(goToArtists, goToGenres)
                },
            ) { innerPadding ->
                Navigation(navController, modifier = Modifier.padding(innerPadding))
            }
        }
        else -> {
            Row {
                AnimatedVisibility(visible = navigationType == MusicBrainNavigationType.NAVIGATION_RAIL) {
                    NavigationRail(
                        selectedDestination = navController.currentDestination,
                        onTabPressed = { node: String -> navController.navigate(node) },
                    )
                }
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    topBar = {
                        AppBar(
                            currentScreenTitle = currentScreenTitle,
                        )
                    },
                ) { innerPadding ->

                    Navigation(navController, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
