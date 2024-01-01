package com.example.musicbrain.ui.instrumentDetailScreen

/**
 * Sealed interface representing the possible states of the artist API request.
 */
sealed interface InstrumentApiState {
    /**
     * Represents a successful API response.
     */
    object Success : InstrumentApiState

    /**
     * Represents a not found API response.
     */
    object NotFound : InstrumentApiState

    /**
     * Represents an error in the API response.
     */
    object Error : InstrumentApiState

    /**
     * Represents the loading state while waiting for the API response.
     */
    object Loading : InstrumentApiState
}
