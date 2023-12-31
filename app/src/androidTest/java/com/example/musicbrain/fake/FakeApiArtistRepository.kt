package com.example.musicbrain.fake

import com.example.musicbrain.data.ArtistRepository
import com.example.musicbrain.model.Artist
import com.example.musicbrain.network.asDomainObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeApiArtistRepository: ArtistRepository {

    override fun getArtists(): Flow<List<Artist>> = flow {
        emit(
            FakeDataSource.apiArtists.map { it.asDomainObject() }
        )
    }

    override fun getArtist(id: String): Flow<Artist> = flow {
        emit(
            FakeDataSource.apiArtists.map { it.asDomainObject() }.find { it.id == id }!!
        )

    }

    override fun searchArtists(query: String): Flow<List<Artist>> = flow {
        emit(
            FakeDataSource.apiArtists.map { it.asDomainObject() }.filter {
                it.name.contains(query, ignoreCase = true)
            }
        )
    }

    override suspend fun refresh() {

    }

    override suspend fun refreshSearch(search: String) {
    }

    override suspend fun refreshOne(id: String) {

    }
}