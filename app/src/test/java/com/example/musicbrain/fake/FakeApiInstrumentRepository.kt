package com.example.musicbrain.fake

import com.example.musicbrain.data.InstrumentRepository
import com.example.musicbrain.model.Instrument
import com.example.musicbrain.network.asDomainObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeApiInstrumentRepository: InstrumentRepository {

    override fun getInstruments(query: String?): Flow<List<Instrument>> = flow {
        emit(
            FakeDataSource.apiInstruments.map { it.asDomainObject() }.filter {
                it.name.contains(query!!, ignoreCase = true)
            }
        )
    }

    override fun getInstrument(id: String): Flow<Instrument> = flow {
        FakeDataSource.apiInstruments.map { it.asDomainObject() }.find { it.id == id }
    }

    override suspend fun refresh() {

    }

    override suspend fun refreshSearch(search: String) {

    }

    override suspend fun refreshOne(id: String) {

    }
}