package com.example.musicbrain.fake

import com.example.musicbrain.data.InstrumentRepository
import com.example.musicbrain.model.Instrument
import com.example.musicbrain.network.asDomainObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * A fake implementation of the [InstrumentRepository] interface for testing purposes.
 * It uses a [FakeDataSource] to simulate the behavior of fetching instrument data from an API.
 */
class FakeApiInstrumentRepository : InstrumentRepository {
    /**
     * Emits a flow of instruments by mapping the instruments from the [FakeDataSource] to domain objects.
     */
    override fun getInstruments(): Flow<List<Instrument>> =
        flow {
            emit(
                FakeDataSource.apiInstruments.map { it.asDomainObject() },
            )
        }

    /**
     * Emits a flow containing a single instrument with the specified ID by finding it
     * in the list of instruments from [FakeDataSource].
     *
     * @param id The ID of the instrument to retrieve.
     */
    override fun getInstrument(id: String): Flow<Instrument> =
        flow {
            emit(
                FakeDataSource.apiInstruments.map { it.asDomainObject() }.find { it.id == id }!!,
            )
        }

    /**
     * Emits a flow of instruments filtered based on the provided query.
     *
     * @param query The search query to filter instruments by.
     */
    override fun searchInstruments(query: String): Flow<List<Instrument>> =
        flow {
            emit(
                FakeDataSource.apiInstruments.map { it.asDomainObject() }.filter {
                    it.name.contains(query, ignoreCase = true)
                },
            )
        }

    /**
     * Unimplemented method, as this is a fake repository and does not perform any actual data refresh.
     */
    override suspend fun refresh() { }

    /**
     * No-op method, as this is a fake repository and does not perform any actual data refresh.
     *
     * @param search The search query (not used in this implementation).
     */
    override suspend fun refreshSearch(search: String) { }

    /**
     * No-op method, as this is a fake repository and does not perform any actual data refresh.
     *
     * @param id The ID of the instrument (not used in this implementation).
     */
    override suspend fun refreshOne(id: String) { }
}
