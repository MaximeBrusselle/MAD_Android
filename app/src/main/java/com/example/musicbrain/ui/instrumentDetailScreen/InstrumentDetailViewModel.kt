package com.example.musicbrain.ui.instrumentDetailScreen

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
import com.example.musicbrain.model.Instrument
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException

class InstrumentDetailViewModel(
    private val instrumentRepository: InstrumentRepository
): ViewModel() {
    private val _instrument = MutableStateFlow<Instrument?>(null)
    val instrument: StateFlow<Instrument?> = _instrument
    var instrumentApiState: InstrumentApiState by mutableStateOf(InstrumentApiState.Loading)
        private set

    suspend fun getApiInstrument(instrumentId: String) {
        try {
            instrumentApiState = InstrumentApiState.Loading
            viewModelScope.launch { instrumentRepository.refreshOne(id = instrumentId) }
            val instrumentDetail = instrumentRepository.getInstrument(id = instrumentId).first()
            _instrument.value = instrumentDetail
            instrumentApiState = InstrumentApiState.Success
        } catch (e: IOException) {
            Log.e("InstrumentViewModel", "getApiInstrument: ${e.message}")
            instrumentApiState = InstrumentApiState.Error
        }
    }

    companion object {
        private var Instance: InstrumentDetailViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicBrainApplication)
                    val instrumentRepository = application.container.instrumentRepository
                    Instance = InstrumentDetailViewModel(
                        instrumentRepository = instrumentRepository
                    )
                }
                Instance!!
            }
        }
    }
}