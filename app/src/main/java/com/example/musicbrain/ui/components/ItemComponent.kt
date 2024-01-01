package com.example.musicbrain.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

/**
 * Custom composable for displaying an item component with details such as name and type.
 *
 * @param item The data class representing the item's information.
 * @param toDetailPage Callback function to navigate to the detail page with the item's ID.
 * @param tagStart The tag prefix for testing purposes.
 */
@Composable
fun ItemComponent(
    item: ListComponentItem,
    toDetailPage: (id: String) -> Unit,
    tagStart: String,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    toDetailPage(item.id)
                }
                .testTag("$tagStart-${item.id}"),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(item.name)

        Text(text = item.type)
    }
}
