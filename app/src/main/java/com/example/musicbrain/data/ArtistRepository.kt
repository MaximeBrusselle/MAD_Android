package com.example.musicbrain.data

import android.util.Log
import com.example.musicbrain.data.database.ArtistDao
import com.example.musicbrain.data.database.asDbArtist
import com.example.musicbrain.data.database.asDomainArtist
import com.example.musicbrain.data.database.asDomainArtists
import com.example.musicbrain.model.Artist
import com.example.musicbrain.network.MusicBrainApiService
import com.example.musicbrain.network.asDomainObject
import com.example.musicbrain.network.asDomainObjects
import com.example.musicbrain.network.getArtistAsFlow
import com.example.musicbrain.network.getArtistsAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException

/**
 * Repository interface for managing artist-related data.
 */
interface ArtistRepository {
    /**
     * Retrieves a flow of the list of all artists.
     *
     * @return A flow emitting a list of [Artist] objects.
     */
    fun getArtists(): Flow<List<Artist>>

    /**
     * Retrieves a flow of a single artist with the specified ID.
     *
     * @param id The unique identifier of the artist.
     * @return A flow emitting a single [Artist] object.
     */
    fun getArtist(id: String): Flow<Artist>

    /**
     * Searches for artists based on the specified query.
     *
     * @param query The search query for artist names.
     * @return A flow emitting a list of [Artist] objects.
     */
    fun searchArtists(query: String): Flow<List<Artist>>

    /**
     * Refreshes the list of artists based on the specified search query.
     *
     * @param search The search query for artist names.
     */
    suspend fun refreshSearch(search: String)

    /**
     * Refreshes the entire list of artists.
     */
    suspend fun refresh()

    /**
     * Refreshes a specific artist with the specified ID.
     *
     * @param id The unique identifier of the artist.
     */
    suspend fun refreshOne(id: String)
}

/**
 * Repository implementation for managing artist-related data from the local Room database and the remote API.
 *
 * @property artistDao The Data Access Object (DAO) for artists in the local Room database.
 * @property musicbrainApiService The API service for fetching artist data from the remote server.
 */
class ApiArtistsRepository(
    private val artistDao: ArtistDao,
    private val musicbrainApiService: MusicBrainApiService,
) : ArtistRepository {
    /**
     * Retrieves a [Flow] of the list of all artists from the local Room database.
     * If the local database is empty, triggers a refresh from the remote API.
     *
     * @return A [Flow] emitting a list of [Artist] objects.
     */
    override fun getArtists(): Flow<List<Artist>> {
        return artistDao.getAllItems().map {
            it.asDomainArtists()
        }.onEach {
            if (it.isEmpty()) {
                refresh()
            }
        }
    }

    /**
     * Retrieves a [Flow] of a single artist with the specified ID from the local Room database.
     * If the local database does not contain the artist, triggers a refresh for that specific artist from the remote API.
     *
     * @param id The unique identifier of the artist.
     * @return A [Flow] emitting a single [Artist] object.
     */
    override fun getArtist(id: String): Flow<Artist> {
        return artistDao.getItem(id).map {
            if (it.name.isEmpty()) {
                refreshOne(id)
            }
            it.asDomainArtist()
        }
    }

    /**
     * Searches for artists in the local Room database based on the specified query.
     * If no matching artists are found locally, triggers a refresh for that specific search query from the remote API.
     *
     * @param query The search query for artist names.
     * @return A [Flow] emitting a list of [Artist] objects.
     */
    override fun searchArtists(query: String): Flow<List<Artist>> {
        return artistDao.getSearchItems(query).map {
            it.asDomainArtists()
        }.onEach {
            if (it.isEmpty()) {
                refreshSearch(query)
            }
        }
    }

    /**
     * Refreshes the entire list of artists from the remote API and inserts them into the local Room database.
     */
    override suspend fun refresh() {
        try {
            musicbrainApiService.getArtistsAsFlow().asDomainObjects().collect { value ->
                for (artist in value) {
                    artistDao.insert(artist.asDbArtist())
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.e("ApiArtistsRepository", "refresh: ${e.message}")
        }
    }

    /**
     * Refreshes the list of artists based on the specified search query from the remote API
     * and inserts them into the local Room database.
     *
     * @param search The search query for artist names.
     */
    override suspend fun refreshSearch(search: String) {
        try {
            musicbrainApiService.getArtistsAsFlow(search).asDomainObjects().collect { value ->
                for (artist in value) {
                    artistDao.insert(artist.asDbArtist())
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.e("ApiArtistsRepository", "refreshSearch: ${e.message}")
        }
    }

    /**
     * Refreshes a specific artist with the specified ID from the remote API and inserts it into the local Room database.
     *
     * @param id The unique identifier of the artist.
     */
    override suspend fun refreshOne(id: String) {
        try {
            musicbrainApiService.getArtistAsFlow(id).collect { value ->
                artistDao.insert(value.asDomainObject().asDbArtist())
            }
        } catch (e: SocketTimeoutException) {
            Log.e("ApiArtistsRepository", "refreshOne: ${e.message}")
        }
    }
}
