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
import androidx.compose.ui.unit.dp

data class ListComponentItem(
    val id: String,
    val name: String,
    val type: String,
)

@Composable
fun ListComponent(
    items: List<ListComponentItem>,
    toDetailPage: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        LazyColumn {
            val grouped = items.groupBy { it.name.first().uppercaseChar() }
            grouped.forEach { (initial, items) ->
                item {
                    Box(modifier = modifier
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .fillMaxWidth()) {
                        Text(initial.toString(), modifier = modifier.padding(8.dp))
                    }
                }
                itemsIndexed(items) { index, item ->
                    ItemComponent(
                        item = item,
                        toDetailPage = toDetailPage
                    )
                    if (index != items.lastIndex) {
                        Divider()
                    }
                }
            }
        }
    }

}