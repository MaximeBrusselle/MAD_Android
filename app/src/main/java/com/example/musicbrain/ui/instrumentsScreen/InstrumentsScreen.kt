package com.example.musicbrain.ui.instrumentsScreen

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicbrain.ui.components.ListComponent
import com.example.musicbrain.ui.components.ListComponentItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstrumentsScreen(
    toDetailPage: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    instrumentsViewModel: InstrumentsViewModel = viewModel(
        factory = InstrumentsViewModel.Factory
    ),
) {
    val instrumentsState by instrumentsViewModel.uiState.collectAsState()
    val instrumentListState by instrumentsViewModel.uiListState.collectAsState()

    val instrumentApiState = instrumentsViewModel.instrumentsApiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            query = instrumentsState.query,
            onQueryChange = instrumentsViewModel::updateQuery,
            placeholder = {
                Text("Search Instrument")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                if (instrumentsState.query.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        modifier = modifier.clickable {
                            if (instrumentsState.query.isNotEmpty()) {
                                instrumentsViewModel.clearQuery()
                                instrumentsViewModel.searchInstruments()
                                instrumentsViewModel.setActive(false)
                            }
                        }
                    )
                }
            },
            active = instrumentsState.active,
            onActiveChange = instrumentsViewModel::setActive,
            modifier = modifier.fillMaxWidth(),
            onSearch = {
                instrumentsViewModel.searchInstruments()
            }
        ) {
            if (instrumentsState.searchHistory.isEmpty()) {
                Text("No recent searches", modifier = modifier.padding(8.dp))
            } else {
                instrumentsState.searchHistory.reversed().forEach { query ->
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                instrumentsViewModel.updateQuery(query)
                                instrumentsViewModel.searchInstruments()
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

        when (instrumentApiState) {
            InstrumentsApiState.Loading -> {
                Text("Loading...")
            }
            InstrumentsApiState.Error -> {
                Text("Error")
            }
            InstrumentsApiState.NotFound -> {
                Text("Not Found")
            }
            InstrumentsApiState.Success -> {
                ListComponent(
                    items = instrumentListState.instruments.map {
                        ListComponentItem(
                            id = it.id,
                            name = it.name,
                            type = it.type
                        )
                    },
                    toDetailPage = toDetailPage,
                    modifier = modifier,
                    tagStart = "Instrument"
                )
            }
        }
    }
}
