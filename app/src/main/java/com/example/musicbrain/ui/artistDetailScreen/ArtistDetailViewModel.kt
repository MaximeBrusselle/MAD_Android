package com.example.musicbrain.ui.artistDetailScreen

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
import com.example.musicbrain.model.Artist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel for the Artist Detail screen. Handles fetching details for a specific artist.
 *
 * @param artistRepository Repository for accessing artist data.
 */
class ArtistDetailViewModel(
    private val artistRepository: ArtistRepository,
) : ViewModel() {
    /**
     * Mutable state flow to hold the artist details.
     */
    private val _artist = MutableStateFlow<Artist?>(null)

    /**
     * Public state flow to observe the artist details.
     */
    val artist: StateFlow<Artist?> = _artist

    /**
     * State representing the API call status for the artist details.
     */
    var artistApiState: ArtistApiState by mutableStateOf(ArtistApiState.Loading)
        private set

    /**
     * Fetch artist details for the specified artist ID.
     *
     * @param artistId The unique identifier of the artist.
     */
    suspend fun getApiArtist(artistId: String) {
        runCatching {
            artistApiState = ArtistApiState.Loading
            viewModelScope.launch { artistRepository.refreshOne(id = artistId) }
            val artistDetail = artistRepository.getArtist(id = artistId).first()
            _artist.value = artistDetail
        }.onSuccess {
            artistApiState = ArtistApiState.Success
        }.onFailure {
            Log.e("ArtistViewModel", "getApiArtist: ${it.message}")
            artistApiState = ArtistApiState.Error
        }
    }

    companion object {
        /**
         * Singleton instance of [ArtistDetailViewModel].
         */
        private var instance: ArtistDetailViewModel? = null

        /**
         * Factory for creating [ArtistDetailViewModel] instances.
         */
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    if (instance == null) {
                        val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicBrainApplication)
                        val artistRepository = application.container.artistRepository
                        instance =
                            ArtistDetailViewModel(
                                artistRepository = artistRepository,
                            )
                    }
                    instance!!
                }
            }
    }
}
