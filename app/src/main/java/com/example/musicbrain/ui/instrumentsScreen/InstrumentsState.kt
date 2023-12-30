package com.example.musicbrain.ui.instrumentsScreen

import com.example.musicbrain.model.Instrument

data class InstrumentsState(
    val query: String = "",
    val active: Boolean = false,
    var searchHistory: List<String> = listOf()
)

data class InstrumentListState(val instruments: List<Instrument> = listOf())

sealed interface InstrumentsApiState {
    object Success : InstrumentsApiState
    object NotFound : InstrumentsApiState
    object Error : InstrumentsApiState
    object Loading : InstrumentsApiState
}