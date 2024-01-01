package com.example.musicbrain.fake

import com.example.musicbrain.data.ArtistRepository
import com.example.musicbrain.model.Artist
import com.example.musicbrain.network.asDomainObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * A fake implementation of the [ArtistRepository] interface for testing purposes.
 * It uses a [FakeDataSource] to simulate the behavior of fetching artist data from an API.
 */
class FakeApiArtistRepository : ArtistRepository {
    /**
     * Emits a flow of artists by mapping the artists from the [FakeDataSource] to domain objects.
     */
    override fun getArtists(): Flow<List<Artist>> =
        flow {
            emit(
                FakeDataSource.apiArtists.map { it.asDomainObject() },
            )
        }

    /**
     * Emits a flow containing a single artist with the specified ID by finding it
     * in the list of artists from [FakeDataSource].
     *
     * @param id The ID of the artist to retrieve.
     */
    override fun getArtist(id: String): Flow<Artist> =
        flow {
            emit(
                FakeDataSource.apiArtists.map { it.asDomainObject() }.find { it.id == id }!!,
            )
        }

    /**
     * Emits a flow of artists filtered based on the provided query.
     *
     * @param query The search query to filter artists by.
     */
    override fun searchArtists(query: String): Flow<List<Artist>> =
        flow {
            emit(
                FakeDataSource.apiArtists.map { it.asDomainObject() }.filter {
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
     * @param id The ID of the artist (not used in this implementation).
     */
    override suspend fun refreshOne(id: String) { }
}
