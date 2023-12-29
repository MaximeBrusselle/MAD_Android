package com.example.musicbrain.ui.artistScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicbrain.model.Artist

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(
    toDetailPage: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    artistsViewModel: ArtistsViewModel = viewModel(
        factory = ArtistsViewModel.Factory
    ),
) {
    val artistsState by artistsViewModel.uiState.collectAsState()
    val artistListState by artistsViewModel.uiListState.collectAsState()

    val artistApiState = artistsViewModel.artistApiState

    Column(
        modifier = modifier
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
                        modifier = modifier.clickable {
                            if (artistsState.query.isNotEmpty()) {
                                artistsViewModel.clearQuery()
                                artistsViewModel.searchArtists()
                                artistsViewModel.setActive(false)
                            }
                        }
                    )
                }
            },
            active = artistsState.active,
            onActiveChange = artistsViewModel::setActive,
            modifier = modifier.fillMaxWidth(),
            onSearch = {
                artistsViewModel.searchArtists()
            }
        ) {
            if (artistsState.searchHistory.isEmpty()) {
                Text("No recent searches", modifier = modifier.padding(8.dp))
            } else {
                artistsState.searchHistory.reversed().forEach { query ->
                    Row(
                        modifier = modifier
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

        Spacer(modifier = modifier.height(16.dp))

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
                ArtistList(
                    artists = artistListState.artists,
                    toDetailPage = toDetailPage,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ArtistList(
    artists: List<Artist>,
    toDetailPage: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        LazyColumn {
            val grouped = artists.groupBy { it.name.first().uppercaseChar() }
            grouped.forEach { (initial, artists) ->
                item {
                    Box(modifier = modifier
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .fillMaxWidth()) {
                        Text(initial.toString(), modifier = modifier.padding(8.dp))
                    }
                }
                itemsIndexed(artists) { index, artist ->
                    ArtistItem(
                        artist = artist,
                        toDetailPage = toDetailPage
                    )
                    if (index != artists.lastIndex) {
                        Divider()
                    }
                }
            }
        }
    }

}

@Composable
fun ArtistItem(
    artist: Artist,
    toDetailPage: (id: String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                toDetailPage(artist.id)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(artist.name)
        Text(text = artist.type)
    }
}
