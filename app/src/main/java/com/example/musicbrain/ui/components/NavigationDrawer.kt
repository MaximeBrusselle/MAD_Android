package com.example.musicbrain.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavDestination
import com.example.musicbrain.R
import com.example.musicbrain.ui.navigation.NavigationRoutes

/**
 * Composable that represents the content of the navigation drawer.
 *
 * @param selectedDestination The currently selected navigation destination.
 * @param onTabPressed Callback to handle tab selection.
 * @param modifier Modifier for styling and layout customization.
 */
@Composable
fun NavigationDrawerContent(
    selectedDestination: NavDestination?,
    onTabPressed: ((String) -> Unit),
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        for (navItem in NavigationRoutes.values()) {
            NavigationDrawerItem(
                selected = selectedDestination?.route == navItem.name,
                label = {
                    Text(
                        text = navItem.name,
                        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.drawer_padding_header)),
                    )
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = "Navigate to ${navItem.name}",
                        modifier = Modifier.testTag("NavigateTo${navItem.name}Drawer"),
                    )
                },
                colors =
                    NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent,
                    ),
                onClick = { onTabPressed(navItem.name) },
            )
        }
    }
}
