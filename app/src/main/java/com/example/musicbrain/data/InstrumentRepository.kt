package com.example.musicbrain.data


import android.util.Log
import com.example.musicbrain.data.database.InstrumentDao
import com.example.musicbrain.data.database.asDbInstrument
import com.example.musicbrain.data.database.asDomainInstrument
import com.example.musicbrain.data.database.asDomainInstruments
import com.example.musicbrain.model.Instrument
import com.example.musicbrain.network.MusicBrainApiService
import com.example.musicbrain.network.asDomainObject
import com.example.musicbrain.network.asDomainObjects
import com.example.musicbrain.network.getInstrumentAsFlow
import com.example.musicbrain.network.getInstrumentsResponseAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException

interface InstrumentRepository {

    fun getInstruments(query: String? = "a"): Flow<List<Instrument>>
    fun getInstrument(id: String): Flow<Instrument>
    suspend fun refreshSearch(search: String)
    suspend fun refresh()

    suspend fun refreshOne(id: String)
}

class ApiInstrumentsRepository(private val instrumentDao: InstrumentDao, private val musicbrainApiService: MusicBrainApiService) : InstrumentRepository {

    override fun getInstruments(query: String?): Flow<List<Instrument>> {
        return instrumentDao.getAllItems("%$query%").map {
            it.asDomainInstruments()
        }.onEach {
            if (it.isEmpty()) {
                refreshSearch(query ?: "a")
            }
        }
    }

    override fun getInstrument(id: String): Flow<Instrument> {
        return instrumentDao.getItem(id).map {
            if (it.name.isEmpty()) {
                refreshOne(id)
            }
            it.asDomainInstrument()
        }
    }

    override suspend fun refresh() {
        try {
            musicbrainApiService.getInstrumentsResponseAsFlow().asDomainObjects().collect { value ->
                for (instrument in value) {
                    instrumentDao.insert(instrument.asDbInstrument())
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.e("ApiInstrumentsRepository", "refresh: ${e.message}")
        }
    }

    override suspend fun refreshSearch(search: String) {
        try {
            musicbrainApiService.getInstrumentsResponseAsFlow(search).asDomainObjects().collect { value ->
                for (instrument in value) {
                    instrumentDao.insert(instrument.asDbInstrument())
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.e("ApiInstrumentsRepository", "refreshSearch: ${e.message}")
        }
    }

    override suspend fun refreshOne(id: String) {
        try {
            musicbrainApiService.getInstrumentAsFlow(id).collect { value ->
                instrumentDao.insert(value.asDomainObject().asDbInstrument())
            }
        } catch (e: SocketTimeoutException) {
            Log.e("ApiInstrumentsRepository", "refreshOne: ${e.message}")
        }
    }

}