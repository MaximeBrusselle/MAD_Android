package com.example.musicbrain.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MusicBrainApiService {
    @GET("artist")
    @Headers("Accept: application/json", "User-Agent: MyAndroidApp")
    suspend fun searchArtist(
        @Query("query") query: String,
    ): ArtistsResponse

    @GET("artist/{id}")
    @Headers("Accept: application/json", "User-Agent: MyAndroidApp")
    suspend fun getArtist(
        @Query("id") id: String,
    ): ApiArtist

    @GET("instrument")
    @Headers("Accept: application/json", "User-Agent: MyAndroidApp")
    suspend fun searchInstrument(
        @Query("query") query: String,
    ): InstrumentsResponse

    @GET("instrument/{id}")
    @Headers("Accept: application/json", "User-Agent: MyAndroidApp")
    suspend fun getInstrument(
        @Query("id") id: String,
    ): ApiInstrument
}

fun MusicBrainApiService.getArtistsAsFlow(query: String = "a"): Flow<List<ApiArtist>> =
    flow {
        try {
            emit(searchArtist(query).artists)
        } catch (e: HttpException) {
            errorHandler(e.code(), "getArtistsResponseAsFlow", e.message())
        }
    }

fun MusicBrainApiService.getArtistAsFlow(id: String): Flow<ApiArtist> =
    flow {
        try {
            emit(getArtist(id))
        } catch (e: HttpException) {
            errorHandler(e.code(), "getArtistAsFlow", e.message())
        }
    }

fun MusicBrainApiService.getInstrumentsAsFlow(query: String = "a"): Flow<List<ApiInstrument>> =
    flow {
        try {
            emit(searchInstrument(query).instruments)
        } catch (e: HttpException) {
            errorHandler(e.code(), "getInstrumentsResponseAsFlow", e.message())
        }
    }

fun MusicBrainApiService.getInstrumentAsFlow(id: String): Flow<ApiInstrument> =
    flow {
        try {
            emit(getInstrument(id))
        } catch (e: HttpException) {
            errorHandler(e.code(), "getInstrumentAsFlow", e.message())
        }
    }

fun errorHandler(
    code: Int?,
    tag: String,
    errorMessage: String,
) {
    if (code == null) {
        Log.e("MusicBrainApiService", "$tag: $errorMessage")
    } else {
        Log.e("MusicBrainApiService", "$tag$code: $errorMessage")
    }
}
