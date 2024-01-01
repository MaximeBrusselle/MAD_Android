package com.example.musicbrain.ui.instrumentDetailScreen

sealed interface InstrumentApiState {
    object Success : InstrumentApiState

    object NotFound : InstrumentApiState

    object Error : InstrumentApiState

    object Loading : InstrumentApiState
}
