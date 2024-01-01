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
import com.example.musicbrain.network.getInstrumentsAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException

/**
 * Repository interface for managing instrument-related data.
 */
interface InstrumentRepository {
    /**
     * Retrieves a flow of the list of all instruments.
     *
     * @return A flow emitting a list of [Instrument] objects.
     */
    fun getInstruments(): Flow<List<Instrument>>

    /**
     * Retrieves a flow of a single instrument with the specified ID.
     *
     * @param id The unique identifier of the instrument.
     * @return A flow emitting a single [Instrument] object.
     */
    fun getInstrument(id: String): Flow<Instrument>

    /**
     * Searches for instruments based on the specified query.
     *
     * @param query The search query for instrument names.
     * @return A flow emitting a list of [Instrument] objects.
     */
    fun searchInstruments(query: String): Flow<List<Instrument>>

    /**
     * Refreshes the list of instruments based on the specified search query.
     *
     * @param search The search query for instrument names.
     */
    suspend fun refreshSearch(search: String)

    /**
     * Refreshes the entire list of instruments.
     */
    suspend fun refresh()

    /**
     * Refreshes a specific instrument with the specified ID.
     *
     * @param id The unique identifier of the instrument.
     */
    suspend fun refreshOne(id: String)
}

/**
 * Repository implementation for managing instrument-related data from the local Room database and the remote API.
 *
 * @property instrumentDao The Data Access Object (DAO) for instruments in the local Room database.
 * @property musicbrainApiService The API service for fetching instrument data from the remote server.
 */
class ApiInstrumentsRepository(
    private val instrumentDao: InstrumentDao,
    private val musicbrainApiService: MusicBrainApiService,
) : InstrumentRepository {
    /**
     * Retrieves a [Flow] of the list of all instruments from the local Room database.
     * If the local database is empty, triggers a refresh from the remote API.
     *
     * @return A [Flow] emitting a list of [Instrument] objects.
     */
    override fun getInstruments(): Flow<List<Instrument>> {
        return instrumentDao.getAllItems().map {
            it.asDomainInstruments()
        }.onEach {
            if (it.isEmpty()) {
                refresh()
            }
        }
    }

    /**
     * Retrieves a [Flow] of a single instrument with the specified ID from the local Room database.
     * If the local database does not contain the instrument, triggers a refresh for that specific instrument from the remote API.
     *
     * @param id The unique identifier of the instrument.
     * @return A [Flow] emitting a single [Instrument] object.
     */
    override fun getInstrument(id: String): Flow<Instrument> {
        return instrumentDao.getItem(id).map {
            if (it.name.isEmpty()) {
                refreshOne(id)
            }
            it.asDomainInstrument()
        }
    }

    /**
     * Searches for instruments in the local Room database based on the specified query.
     * If no matching instruments are found locally, triggers a refresh for that specific search query from the remote API.
     *
     * @param query The search query for instrument names.
     * @return A [Flow] emitting a list of [Instrument] objects.
     */
    override fun searchInstruments(query: String): Flow<List<Instrument>> {
        return instrumentDao.getSearchItems(query).map {
            it.asDomainInstruments()
        }.onEach {
            if (it.isEmpty()) {
                refreshSearch(query)
            }
        }
    }

    /**
     * Refreshes the entire list of instruments from the remote API and inserts them into the local Room database.
     */
    override suspend fun refresh() {
        try {
            musicbrainApiService.getInstrumentsAsFlow().asDomainObjects().collect { value ->
                for (instrument in value) {
                    instrumentDao.insert(instrument.asDbInstrument())
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.e("ApiInstrumentsRepository", "refresh: ${e.message}")
        }
    }

    /**
     * Refreshes the list of instruments based on the specified search query from the remote API
     * and inserts them into the local Room database.
     *
     * @param search The search query for instrument names.
     */
    override suspend fun refreshSearch(search: String) {
        try {
            musicbrainApiService.getInstrumentsAsFlow(search).asDomainObjects().collect { value ->
                for (instrument in value) {
                    instrumentDao.insert(instrument.asDbInstrument())
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.e("ApiInstrumentsRepository", "refreshSearch: ${e.message}")
        }
    }

    /**
     * Refreshes a specific instrument with the specified ID from the remote API and inserts it into the local Room database.
     *
     * @param id The unique identifier of the instrument.
     */
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
