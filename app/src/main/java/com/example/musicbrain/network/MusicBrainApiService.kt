package com.example.musicbrain.network

import retrofit2.HttpException
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MusicBrainApiService {
    @GET("artist")
    @Headers("Accept: application/json", "User-Agent: MyAndroidApp")
    suspend fun searchArtist(@Query("query") query: String): ArtistResponse
}

fun MusicBrainApiService.getArtistResponseAsFlow(query: String = "a"): Flow<ArtistResponse> = flow {
    try {
        emit(searchArtist(query))
    }
    catch(e: HttpException){
        when (e.code()) {
            400 ->
                Log.e("MusicBrainApiService", "getArtistResponseAsFlow400: ${e.message}")
            401->
                Log.e("MusicBrainApiService", "getArtistResponseAsFlow401: ${e.message}")
            403->
                Log.e("MusicBrainApiService", "getArtistResponseAsFlow403: ${e.message}")
            404->
                Log.e("MusicBrainApiService", "getArtistResponseAsFlow404: ${e.message}")
            500 ->
                Log.e("MusicBrainApiService", "getArtistResponseAsFlow500: ${e.message}")
            503 ->
                Log.e("MusicBrainApiService", "getArtistResponseAsFlow503: ${e.message}")
            else ->
                Log.e("MusicBrainApiService", "getArtistResponseAsFlowOther: ${e.message}")
        }
    }
}