package com.example.musicbrain.ui.artistDetailScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ArtistDetailScreen(
    artistId: String,
    onBack: () -> Unit
) {
    Text(
        text = artistId,
        modifier = Modifier.fillMaxSize().clickable(onClick = onBack),
    )
}