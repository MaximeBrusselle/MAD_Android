package com.example.musicbrain.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ItemComponent(
    item: ListComponentItem,
    toDetailPage: (id: String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                toDetailPage(item.id)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(item.name)
        Text(text = item.type)
    }
}