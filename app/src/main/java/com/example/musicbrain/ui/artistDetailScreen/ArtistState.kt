package com.example.musicbrain.ui.artistDetailScreen

import com.example.musicbrain.model.Artist

sealed interface ArtistApiState {
    object Success : ArtistApiState
    object NotFound : ArtistApiState
    object Error : ArtistApiState
    object Loading : ArtistApiState
}