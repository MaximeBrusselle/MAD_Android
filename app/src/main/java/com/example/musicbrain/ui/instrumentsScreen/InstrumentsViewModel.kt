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
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InstrumentsViewModel(private val instrumentRepository: InstrumentRepository): ViewModel() {
    private val _uiState =
        MutableStateFlow(
            InstrumentsState()
        )
    val uiState: StateFlow<InstrumentsState> = _uiState.asStateFlow()
    lateinit var uiListState: StateFlow<InstrumentListState>

    // initial value is Loading
    var instrumentsApiState: InstrumentsApiState by mutableStateOf(InstrumentsApiState.Loading)
        private set

    init {
        getApiInstruments()
    }

    private fun getApiInstruments() {
        try {
            viewModelScope.launch { instrumentRepository.refresh() }

            uiListState = instrumentRepository.getInstruments(query = _uiState.value.query).map { InstrumentListState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = InstrumentListState()
                )
            if (uiListState.value.instruments.isEmpty()) {
                instrumentsApiState = InstrumentsApiState.NotFound
            }
            instrumentsApiState = InstrumentsApiState.Success
        } catch (e: IOException) {
            Log.e("InstrumentsViewModel", "getApiInstruments: ${e.message}")
            instrumentsApiState = InstrumentsApiState.Error
        }
    }

    fun searchInstruments() {
        if (_uiState.value.query.isEmpty()) {
            getApiInstruments()
        } else {
            try {
                viewModelScope.launch { instrumentRepository.refreshSearch(_uiState.value.query) }

                uiListState = instrumentRepository.getInstruments(_uiState.value.query)
                    .map { InstrumentListState(it) }
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(),
                        initialValue = InstrumentListState()
                    )
                _uiState.update {
                    it.copy(
                        active = false,
                        searchHistory = it.searchHistory + it.query
                    )
                }
                instrumentsApiState = InstrumentsApiState.Success
            } catch (e: IOException) {
                Log.e("InstrumentsViewModel", "searchInstruments: ${e.message}")
                instrumentsApiState = InstrumentsApiState.Error
            }
        }
    }

    fun updateQuery(text: String) {
        _uiState.update {
            it.copy(
                query = text
            )
        }
    }

    fun clearQuery() {
        _uiState.update {
            it.copy(
                query = ""
            )
        }
    }

    fun setActive(active: Boolean) {
        _uiState.update {
            it.copy(
                active = active
            )
        }
    }

    companion object {
        private var Instance: InstrumentsViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicBrainApplication)
                    val instrumentRepository = application.container.instrumentRepository
                    Instance = InstrumentsViewModel(instrumentRepository = instrumentRepository)
                }
                Instance!!
            }
        }
    }
}