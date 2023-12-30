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
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArtistsViewModel(private val artistRepository: ArtistRepository): ViewModel() {
    private val _uiState =
        MutableStateFlow(
            ArtistsState()
        )
    val uiState: StateFlow<ArtistsState> = _uiState.asStateFlow()
    lateinit var uiListState: StateFlow<ArtistListState>

    // initial value is Loading
    var artistsApiState: ArtistsApiState by mutableStateOf(ArtistsApiState.Loading)
        private set

    init {
        getApiArtists()
    }

    private fun getApiArtists() {
        try {
            viewModelScope.launch { artistRepository.refresh() }

            uiListState = artistRepository.getArtists(query = _uiState.value.query).map { ArtistListState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = ArtistListState()
                )
            if (uiListState.value.artists.isEmpty()) {
                artistsApiState = ArtistsApiState.NotFound
            }
            artistsApiState = ArtistsApiState.Success
        } catch (e: IOException) {
            Log.e("ArtistsViewModel", "getApiArtists: ${e.message}")
            artistsApiState = ArtistsApiState.Error
        }
    }

    fun searchArtists() {
        if (_uiState.value.query.isEmpty()) {
            getApiArtists()
        } else {
            try {
                viewModelScope.launch { artistRepository.refreshSearch(_uiState.value.query) }

                uiListState = artistRepository.getArtists(_uiState.value.query)
                    .map { ArtistListState(it) }
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(),
                        initialValue = ArtistListState()
                    )
                _uiState.update {
                    it.copy(
                        active = false,
                        searchHistory = it.searchHistory + it.query
                    )
                }
                artistsApiState = ArtistsApiState.Success
            } catch (e: IOException) {
                Log.e("ArtistsViewModel", "searchArtists: ${e.message}")
                artistsApiState = ArtistsApiState.Error
            }
        }
    }

    fun updateQuery(text: String) {
        _uiState.update {
            it.copy(
                query = text
            )
        }
    }

    fun clearQuery() {
        _uiState.update {
            it.copy(
                query = ""
            )
        }
    }

    fun setActive(active: Boolean) {
        _uiState.update {
            it.copy(
                active = active
            )
        }
    }

    companion object {
        private var Instance: ArtistsViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicBrainApplication)
                    val artistRepository = application.container.artistRepository
                    Instance = ArtistsViewModel(artistRepository = artistRepository)
                }
                Instance!!
            }
        }
    }
}