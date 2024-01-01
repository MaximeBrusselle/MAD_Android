package com.example.musicbrain.ui.artistDetailScreen

/**
 * Sealed interface representing the possible states of the artist API request.
 */
sealed interface ArtistApiState {
    /**
     * Represents a successful API response.
     */
    object Success : ArtistApiState

    /**
     * Represents a not found API response.
     */
    object NotFound : ArtistApiState

    /**
     * Represents an error in the API response.
     */
    object Error : ArtistApiState

    /**
     * Represents the loading state while waiting for the API response.
     */
    object Loading : ArtistApiState
}
