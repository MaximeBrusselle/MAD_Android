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
import java.io.IOException

class ArtistDetailViewModel(
    private val artistRepository: ArtistRepository,
) : ViewModel() {
    private val _artist = MutableStateFlow<Artist?>(null)
    val artist: StateFlow<Artist?> = _artist
    var artistApiState: ArtistApiState by mutableStateOf(ArtistApiState.Loading)
        private set

    suspend fun getApiArtist(artistId: String) {
        try {
            artistApiState = ArtistApiState.Loading
            viewModelScope.launch { artistRepository.refreshOne(id = artistId) }
            val artistDetail = artistRepository.getArtist(id = artistId).first()
            _artist.value = artistDetail
            artistApiState = ArtistApiState.Success
        } catch (e: IOException) {
            Log.e("ArtistViewModel", "getApiArtist: ${e.message}")
            artistApiState = ArtistApiState.Error
        }
    }

    companion object {
        private var instance: ArtistDetailViewModel? = null
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
