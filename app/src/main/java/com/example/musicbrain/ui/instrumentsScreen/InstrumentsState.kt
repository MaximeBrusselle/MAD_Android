package com.example.musicbrain.ui.instrumentsScreen

import com.example.musicbrain.model.Instrument

/**
 * Data class representing the state of the Instruments screen.
 *
 * @property query The search query for instruments.
 * @property active Boolean indicating if the search bar is active.
 * @property searchHistory List of recent search queries.
 */
data class InstrumentsState(
    val query: String = "",
    val active: Boolean = false,
    var searchHistory: List<String> = listOf(),
)

/**
 * Data class representing the state of the list of instruments.
 *
 * @property instruments List of instruments.
 */
data class InstrumentListState(val instruments: List<Instrument> = listOf())

/**
 * Sealed interface representing the API state for the Instruments screen.
 */
sealed interface InstrumentsApiState {
    /**
     * Represents a successful API response.
     */
    object Success : InstrumentsApiState

    /**
     * Represents a not found API response.
     */
    object NotFound : InstrumentsApiState

    /**
     * Represents an error in the API response.
     */
    object Error : InstrumentsApiState

    /**
     * Represents the loading state while waiting for the API response.
     */
    object Loading : InstrumentsApiState
}
