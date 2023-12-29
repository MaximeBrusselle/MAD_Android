package com.example.musicbrain.ui.artistScreen

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicbrain.model.Artist

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(
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
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            query = artistsState.query,
            onQueryChange = artistsViewModel::updateQuery,
            placeholder = {
                Text("Search Artist")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                if (artistsState.query.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        modifier = Modifier.clickable {
                            if (artistsState.query.isNotEmpty()) {
                                artistsViewModel.clearQuery()
                                artistsViewModel.searchArtists()
                            }
                        }
                    )
                }
            },
            active = artistsState.active,
            onActiveChange = artistsViewModel::setActive,
            modifier = Modifier.fillMaxWidth(),
            onSearch = {
                artistsViewModel.searchArtists()
            }
        ) {
            if (artistsState.searchHistory.isEmpty()) {
                Text("No recent searches", modifier = Modifier.padding(8.dp))
            } else {
                artistsState.searchHistory.reversed().forEach { query ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                artistsViewModel.updateQuery(query)
                                artistsViewModel.searchArtists()
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "History Icon",
                        )
                        Text(query)
                    }
                }
            }
        }

        Button(
            onClick = {
                artistsViewModel.searchArtists()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (artistApiState) {
            ArtistApiState.Loading -> {
                Text("Loading...")
            }
            ArtistApiState.Error -> {
                Text("Error")
            }
            ArtistApiState.NotFound -> {
                Text("Not Found")
            }
            ArtistApiState.Success -> {
                ArtistList(artistListState.artists)
            }
        }
    }
}

@Composable
fun ArtistList(artists: List<Artist>) {
    LazyColumn {
        artists.subList(0, artists.count().coerceAtMost(20)).forEach { artist ->
            item {
                ArtistItem(artist)
            }
        }
    }
}

@Composable
fun ArtistItem(artist: Artist) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(artist.name)
    }
}
