package com.example.musicbrain.ui.artistsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicbrain.ui.components.ListComponent
import com.example.musicbrain.ui.components.ListComponentItem

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

    val artistApiState = artistsViewModel.artistsApiState

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
            modifier = modifier.fillMaxWidth().testTag("ArtistsSearchBar"),
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
            ArtistsApiState.Loading -> {
                Text("Loading...")
            }
            ArtistsApiState.Error -> {
                Text("Error")
            }
            ArtistsApiState.NotFound -> {
                Text("Not Found")
            }
            ArtistsApiState.Success -> {
                ListComponent(
                    items = artistListState.artists.map {
                        ListComponentItem(
                            id = it.id,
                            name = it.name,
                            type = it.type,
                        )
                    },
                    toDetailPage = toDetailPage,
                    modifier = modifier,
                    tagStart = "Artist"
                )
            }
        }
    }
}
