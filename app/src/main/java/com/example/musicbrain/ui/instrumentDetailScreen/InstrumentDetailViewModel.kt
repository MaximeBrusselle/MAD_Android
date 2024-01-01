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

/**
 * ViewModel for managing the data and business logic related to instrument details.
 *
 * @param instrumentRepository Repository for accessing instrument-related data.
 */
class InstrumentDetailViewModel(
    private val instrumentRepository: InstrumentRepository,
) : ViewModel() {
    /**
     * Mutable state flow to hold the instrument details.
     */
    private val _instrument = MutableStateFlow<Instrument?>(null)

    /**
     * Public state flow to observe the instrument details.
     */
    val instrument: StateFlow<Instrument?> = _instrument

    /**
     * State representing the API call status for the instrument details.
     */
    var instrumentApiState: InstrumentApiState by mutableStateOf(InstrumentApiState.Loading)
        private set

    /**
     * Function to fetch instrument details from the API.
     *
     * @param instrumentId The unique identifier of the instrument.
     */
    suspend fun getApiInstrument(instrumentId: String) {
        runCatching {
            instrumentApiState = InstrumentApiState.Loading
            viewModelScope.launch { instrumentRepository.refreshOne(id = instrumentId) }
            val instrumentDetail = instrumentRepository.getInstrument(id = instrumentId).first()
            _instrument.value = instrumentDetail
        }.onSuccess {
            instrumentApiState = InstrumentApiState.Success
        }.onFailure { exception ->
            Log.e("InstrumentViewModel", "getApiInstrument: ${exception.message}")
            instrumentApiState = InstrumentApiState.Error
        }
    }

    companion object {
        private var instance: InstrumentDetailViewModel? = null

        /**
         * Factory to create an instance of [InstrumentDetailViewModel].
         */
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    if (instance == null) {
                        val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicBrainApplication)
                        val instrumentRepository = application.container.instrumentRepository
                        instance =
                            InstrumentDetailViewModel(
                                instrumentRepository = instrumentRepository,
                            )
                    }
                    instance!!
                }
            }
    }
}
