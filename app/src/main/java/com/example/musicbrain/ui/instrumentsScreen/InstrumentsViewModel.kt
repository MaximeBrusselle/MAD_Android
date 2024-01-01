package com.example.musicbrain.ui.instrumentsScreen

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
import com.example.musicbrain.data.InstrumentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InstrumentsViewModel(
    private val instrumentRepository: InstrumentRepository,
) : ViewModel() {
    /*
     * Mutable state flow to hold the ui state.
     */
    private val _uiState =
        MutableStateFlow(
            InstrumentsState(),
        )

    /**
     * Public state flow to hold the ui state.
     */
    val uiState: StateFlow<InstrumentsState> = _uiState.asStateFlow()

    /**
     * Public state flow to observe the instruments.
     */
    lateinit var uiListState: StateFlow<InstrumentListState>

    /**
     * State representing the API call status for the instruments.
     */
    var instrumentsApiState: InstrumentsApiState by mutableStateOf(InstrumentsApiState.Loading)
        private set

    init {
        getApiInstruments()
    }

    /**
     * Function to retrieve instruments from the API and update the state accordingly.
     */
    private fun getApiInstruments() {
        runCatching {
            viewModelScope.launch { instrumentRepository.refresh() }

            uiListState =
                instrumentRepository.getInstruments().map { InstrumentListState(it) }
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000L),
                        initialValue = InstrumentListState(),
                    )
        }.onSuccess {
            instrumentsApiState = InstrumentsApiState.Success
        }.onFailure { e ->
            Log.e("InstrumentsViewModel", "getApiInstruments: ${e.message}")
            instrumentsApiState = InstrumentsApiState.Error
        }
    }

    /**
     * Function to search for instruments based on the user's query.
     */
    fun searchInstruments() {
        if (_uiState.value.query.isEmpty()) {
            getApiInstruments()
        } else {
            runCatching {
                viewModelScope.launch { instrumentRepository.refreshSearch(_uiState.value.query) }

                uiListState =
                    instrumentRepository.searchInstruments(_uiState.value.query)
                        .map { InstrumentListState(it) }
                        .stateIn(
                            scope = viewModelScope,
                            started = SharingStarted.WhileSubscribed(),
                            initialValue = InstrumentListState(),
                        )
                _uiState.update {
                    it.copy(
                        active = false,
                        searchHistory = it.searchHistory + it.query,
                    )
                }
            }.onSuccess {
                instrumentsApiState = InstrumentsApiState.Success
            }.onFailure { e ->
                Log.e("InstrumentsViewModel", "searchInstruments: ${e.message}")
                instrumentsApiState = InstrumentsApiState.Error
            }
        }
    }

    /**
     * Function to update the search query in the UI state.
     */
    fun updateQuery(text: String) {
        _uiState.update {
            it.copy(
                query = text,
            )
        }
    }

    /**
     * Function to clear the search query in the UI state.
     */
    fun clearQuery() {
        _uiState.update {
            it.copy(
                query = "",
            )
        }
    }

    /**
     * Function to set the active state in the UI state.
     */
    fun setActive(active: Boolean) {
        _uiState.update {
            it.copy(
                active = active,
            )
        }
    }

    companion object {
        /*
         * Singleton instance to ensure a single instance of the ViewModel.
         */
        private var instance: InstrumentsViewModel? = null

        /**
         * Factory for creating the ViewModel.
         */
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    if (instance == null) {
                        val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicBrainApplication)
                        val instrumentRepository = application.container.instrumentRepository
                        instance = InstrumentsViewModel(instrumentRepository = instrumentRepository)
                    }
                    instance!!
                }
            }
    }
}
