package com.example.musicbrain.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

/**
 * Data class representing an item in the ListComponent.
 *
 * @param id The unique identifier for the item.
 * @param name The name of the item.
 * @param type The type or category of the item.
 * @param score The score or rating associated with the item.
 */
data class ListComponentItem(
    val id: String,
    val name: String,
    val type: String,
    val score: Int,
)

/**
 * Custom composable for displaying a list of items grouped by the first letter of their names.
 *
 * @param items The list of [ListComponentItem] representing the items to be displayed.
 * @param toDetailPage Callback function to navigate to the detail page with the item's ID.
 * @param modifier Optional modifier for customizing the appearance of the [ListComponent].
 * @param tagStart The tag prefix for testing purposes.
 */
@Composable
fun ListComponent(
    items: List<ListComponentItem>,
    toDetailPage: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    tagStart: String,
) {
    Box(
        modifier = modifier.testTag("${tagStart}Box"),
    ) {
        LazyColumn {
            val grouped = items.groupBy { it.name.first().uppercaseChar() }
            grouped.forEach { (initial, items) ->
                item {
                    Box(
                        modifier =
                            modifier
                                .background(color = MaterialTheme.colorScheme.primaryContainer)
                                .fillMaxWidth(),
                    ) {
                        Text(initial.toString(), modifier = modifier.padding(8.dp))
                    }
                }
                itemsIndexed(items) { index, item ->
                    ItemComponent(
                        item = item,
                        toDetailPage = toDetailPage,
                        tagStart = tagStart,
                    )
                    if (index != items.lastIndex) {
                        Divider()
                    }
                }
            }
        }
    }
}
