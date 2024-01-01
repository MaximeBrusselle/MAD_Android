package com.example.musicbrain.ui.artistsScreen

import com.example.musicbrain.model.Artist

/**
 * Data class representing the state of the Artists screen.
 *
 * @property query The search query for artists.
 * @property active Boolean indicating if the search bar is active.
 * @property searchHistory List of recent search queries.
 */
data class ArtistsState(
    val query: String = "",
    val active: Boolean = false,
    var searchHistory: List<String> = listOf(),
)

/**
 * Data class representing the state of the list of artists.
 *
 * @property artists List of artists.
 */
data class ArtistListState(
    val artists: List<Artist> = listOf(),
)

/**
 * Sealed interface representing the API state for the Artists screen.
 */
sealed interface ArtistsApiState {
    /**
     * Represents a successful API response.
     */
    object Success : ArtistsApiState

    /**
     * Represents a not found API response.
     */
    object NotFound : ArtistsApiState

    /**
     * Represents an error in the API response.
     */
    object Error : ArtistsApiState

    /**
     * Represents the loading state while waiting for the API response.
     */
    object Loading : ArtistsApiState
}
