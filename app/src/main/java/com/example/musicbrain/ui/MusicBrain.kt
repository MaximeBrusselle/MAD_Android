package com.example.musicbrain.ui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicbrain.ui.artistScreen.ArtistsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicBrain(
    modifier: Modifier = Modifier,
    artistsViewModel: ArtistsViewModel = viewModel(
        factory = ArtistsViewModel.Factory
    ),
) {
    val artistsState by artistsViewModel.uiState.collectAsState()
    val artistListState by artistsViewModel.uiListState.collectAsState()

    val artistApiState = artistsViewModel.artistApiState

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        SearchBar(
            query = artistsState.query,
            onQueryChange = { artistsViewModel.updateQuery(it) },
            modifier = Modifier.fillMaxWidth(),
            active = artistsState.active,
            onActiveChange = { artistsViewModel.setActive(it) },
            content = {
                Text("Search")
            },
            onSearch = {
                artistsViewModel.searchArtists()
            },
        )

        Button(
            onClick = {
                artistsViewModel.searchArtists()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Search Result: ${artistsState.query}")
    }
}
