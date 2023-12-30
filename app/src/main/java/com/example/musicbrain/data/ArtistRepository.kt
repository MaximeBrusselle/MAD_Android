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
import com.example.musicbrain.network.getArtistsResponseAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException

interface ArtistRepository {

    fun getArtists(query: String? = "a"): Flow<List<Artist>>
    fun getArtist(id: String): Flow<Artist>
    suspend fun refreshSearch(search: String)
    suspend fun refresh()

    suspend fun refreshOne(id: String)
}

class ApiArtistsRepository(private val artistDao: ArtistDao, private val musicbrainApiService: MusicBrainApiService) : ArtistRepository {

    override fun getArtists(query: String?): Flow<List<Artist>> {
        return artistDao.getAllItems("%$query%").map {
            it.asDomainArtists()
        }.onEach {
            if (it.isEmpty()) {
                refreshSearch(query ?: "a")
            }
        }
    }

    override fun getArtist(id: String): Flow<Artist> {
        return artistDao.getItem(id).map {
            if (it.name.isEmpty()) {
                refreshOne(id)
            }
            it.asDomainArtist()
        }
    }

    override suspend fun refresh() {
        try {
            musicbrainApiService.getArtistsResponseAsFlow().asDomainObjects().collect { value ->
                for (artist in value) {
                    artistDao.insert(artist.asDbArtist())
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.e("ApiArtistsRepository", "refresh: ${e.message}")
        }
    }

    override suspend fun refreshSearch(search: String) {
        try {
            musicbrainApiService.getArtistsResponseAsFlow(search).asDomainObjects().collect { value ->
                for (artist in value) {
                    artistDao.insert(artist.asDbArtist())
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.e("ApiArtistsRepository", "refreshSearch: ${e.message}")
        }
    }

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