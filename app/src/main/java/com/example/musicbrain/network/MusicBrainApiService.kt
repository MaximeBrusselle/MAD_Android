package com.example.musicbrain.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Retrofit API service for interacting with the MusicBrainz API.
 */
interface MusicBrainApiService {
    /**
     * Searches for artists based on the provided query.
     *
     * @param query The search query for artists.
     * @return An [ArtistsResponse] containing a list of artists.
     */
    @GET("artist")
    @Headers("Accept: application/json", "User-Agent: MyAndroidApp")
    suspend fun searchArtist(
        @Query("query") query: String,
    ): ArtistsResponse

    /**
     * Retrieves detailed information about a specific artist.
     *
     * @param id The unique identifier of the artist.
     * @return An [ApiArtist] representing the requested artist.
     */
    @GET("artist/{id}")
    @Headers("Accept: application/json", "User-Agent: MyAndroidApp")
    suspend fun getArtist(
        @Query("id") id: String,
    ): ApiArtist

    /**
     * Searches for instruments based on the provided query.
     *
     * @param query The search query for instruments.
     * @return An [InstrumentsResponse] containing a list of instruments.
     */
    @GET("instrument")
    @Headers("Accept: application/json", "User-Agent: MyAndroidApp")
    suspend fun searchInstrument(
        @Query("query") query: String,
    ): InstrumentsResponse

    /**
     * Retrieves detailed information about a specific instrument.
     *
     * @param id The unique identifier of the instrument.
     * @return An [ApiInstrument] representing the requested instrument.
     */
    @GET("instrument/{id}")
    @Headers("Accept: application/json", "User-Agent: MyAndroidApp")
    suspend fun getInstrument(
        @Query("id") id: String,
    ): ApiInstrument
}

/**
 * Extension function to get a flow of lists of [ApiArtist] objects as a result of artist search.
 *
 * @param query The search query for artists (default is "a").
 * @return A flow emitting a list of [ApiArtist] objects.
 */
fun MusicBrainApiService.getArtistsAsFlow(query: String = "a"): Flow<List<ApiArtist>> =
    flow {
        try {
            emit(searchArtist(query).artists)
        } catch (e: HttpException) {
            errorHandler(e.code(), "getArtistsAsFlow", e.localizedMessage)
        }
    }

/**
 * Extension function to get a flow of [ApiArtist] as a result of artist retrieval by ID.
 *
 * @param id The unique identifier of the artist.
 * @return A flow emitting an [ApiArtist] object.
 */
fun MusicBrainApiService.getArtistAsFlow(id: String): Flow<ApiArtist> =
    flow {
        try {
            emit(getArtist(id))
        } catch (e: HttpException) {
            errorHandler(e.code(), "getArtistAsFlow", e.localizedMessage)
        }
    }

/**
 * Extension function to get a flow of lists of [ApiInstrument] objects as a result of instrument search.
 *
 * @param query The search query for instruments (default is "a").
 * @return A flow emitting a list of [ApiInstrument] objects.
 */
fun MusicBrainApiService.getInstrumentsAsFlow(query: String = "a"): Flow<List<ApiInstrument>> =
    flow {
        try {
            emit(searchInstrument(query).instruments)
        } catch (e: HttpException) {
            errorHandler(e.code(), "getInstrumentsAsFlow", e.localizedMessage)
        }
    }

/**
 * Extension function to get a flow of [ApiInstrument] as a result of instrument retrieval by ID.
 *
 * @param id The unique identifier of the instrument.
 * @return A flow emitting an [ApiInstrument] object.
 */
fun MusicBrainApiService.getInstrumentAsFlow(id: String): Flow<ApiInstrument> =
    flow {
        try {
            emit(getInstrument(id))
        } catch (e: HttpException) {
            errorHandler(e.code(), "getInstrumentAsFlow", e.localizedMessage)
        }
    }

/**
 * Extension function to handle errors from the API.
 *
 * @param code The HTTP status code of the error.
 * @param tag The tag to use for logging.
 * @param errorMessage The error message to log.
 */
fun errorHandler(
    code: Int?,
    tag: String,
    errorMessage: String?,
) {
    if (code == null) {
        Log.e("MusicBrainApiService", "$tag: $errorMessage")
    } else {
        Log.e("MusicBrainApiService", "$tag$code: $errorMessage")
    }
}
