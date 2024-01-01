package com.example.musicbrain.ui.artistsScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicbrain.MusicBrainApplication
import com.example.musicbrain.data.ArtistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Artists screen.
 *
 * @property artistRepository The repository for handling artists data.
 */
class ArtistsViewModel(
    private val artistRepository: ArtistRepository,
) : ViewModel() {
    /*
     * Mutable state flow to hold the ui state.
     */
    private val _uiState =
        MutableStateFlow(
            ArtistsState(),
        )

    /**
     * Public state flow to hold the ui state.
     */
    val uiState: StateFlow<ArtistsState> = _uiState.asStateFlow()

    /**
     * Public state flow to observe the artists.
     */
    lateinit var uiListState: StateFlow<ArtistListState>

    /**
     * State representing the API call status for the artists.
     */
    var artistsApiState: ArtistsApiState by mutableStateOf(ArtistsApiState.Loading)
        private set

    init {
        getApiArtists()
    }

    /**
     * Function to retrieve artists from the API and update the state accordingly.
     */
    private fun getApiArtists() {
        runCatching {
            viewModelScope.launch { artistRepository.refresh() }
            uiListState =
                artistRepository.getArtists()
                    .map { ArtistListState(it) }
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000L),
                        initialValue = ArtistListState(),
                    )
        }.onSuccess {
            artistsApiState = ArtistsApiState.Success
        }.onFailure { e ->
            Log.e("ArtistsViewModel", "getApiArtists: ${e.message}")
            artistsApiState = ArtistsApiState.Error
        }
    }

    /**
     * Function to search for artists based on the user's query.
     */
    fun searchArtists() {
        if (_uiState.value.query.isEmpty()) {
            getApiArtists()
        } else {
            runCatching {
                viewModelScope.launch { artistRepository.refreshSearch(_uiState.value.query) }

                uiListState =
                    artistRepository.searchArtists(_uiState.value.query)
                        .map { ArtistListState(it) }
                        .stateIn(
                            scope = viewModelScope,
                            started = SharingStarted.WhileSubscribed(),
                            initialValue = ArtistListState(),
                        )

                _uiState.update {
                    it.copy(
                        active = false,
                        searchHistory = it.searchHistory + it.query,
                    )
                }
            }.onSuccess {
                artistsApiState = ArtistsApiState.Success
            }.onFailure { e ->
                Log.e("ArtistsViewModel", "searchArtists: ${e.message}")
                artistsApiState = ArtistsApiState.Error
            }
        }
    }

    /**
     * Function to update the search query in the UI state.
     */
    fun updateQuery(text: String) {
        _uiState.update {
            it.copy(
                query = text,
            )
        }
    }

    /**
     * Function to clear the search query in the UI state.
     */
    fun clearQuery() {
        _uiState.update {
            it.copy(
                query = "",
            )
        }
    }

    /**
     * Function to set the active state in the UI state.
     */
    fun setActive(active: Boolean) {
        _uiState.update {
            it.copy(
                active = active,
            )
        }
    }

    companion object {
        /*
         * Singleton instance to ensure a single instance of the ViewModel.
         */
        private var instance: ArtistsViewModel? = null

        /**
         * Factory for creating the ViewModel.
         */
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    if (instance == null) {
                        val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicBrainApplication)
                        val artistRepository = application.container.artistRepository
                        instance = ArtistsViewModel(artistRepository = artistRepository)
                    }
                    instance!!
                }
            }
    }
}
