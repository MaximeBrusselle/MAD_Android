package com.example.musicbrain.ui.artistsScreen

import com.example.musicbrain.model.Artist

data class ArtistsState(
    val query: String = "",
    val active: Boolean = false,
    var searchHistory: List<String> = listOf()
)

data class ArtistListState(
    val artists: List<Artist> = listOf()
)

sealed interface ArtistsApiState {
    object Success : ArtistsApiState
    object NotFound : ArtistsApiState
    object Error : ArtistsApiState
    object Loading : ArtistsApiState
}