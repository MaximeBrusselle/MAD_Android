package com.example.musicbrain.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavDestination
import com.example.musicbrain.ui.navigation.NavigationRoutes

/**
 * Composable that represents a vertical navigation rail.
 *
 * @param selectedDestination The currently selected navigation destination.
 * @param onTabPressed Callback to handle tab selection.
 * @param modifier Modifier for styling and layout customization.
 */
@Composable
fun NavigationRail(
    selectedDestination: NavDestination?,
    onTabPressed: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        for (navItem in NavigationRoutes.values()) {
            NavigationRailItem(
                selected = selectedDestination?.route == navItem.name,
                onClick = { onTabPressed(navItem.name) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = "Navigate to ${navItem.name}",
                        modifier = Modifier.testTag("NavigateTo${navItem.name}Rail"),
                    )
                },
            )
        }
    }
}
