package com.example.musicbrain.ui.artistDetailScreen

sealed interface ArtistApiState {
    object Success : ArtistApiState
    object NotFound : ArtistApiState
    object Error : ArtistApiState
    object Loading : ArtistApiState
}