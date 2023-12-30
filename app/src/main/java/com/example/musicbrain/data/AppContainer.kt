package com.example.musicbrain.data

import android.content.Context
import com.example.musicbrain.data.database.MusicDb
import com.example.musicbrain.network.NetworkConnectionInterceptor
import com.example.musicbrain.network.MusicBrainApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppContainer {
    val artistRepository: ArtistRepository
    val instrumentRepository: InstrumentRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    private val networkCheck = NetworkConnectionInterceptor(context)
    private val client = OkHttpClient.Builder()
        .addInterceptor(networkCheck)
        .build()

    private val baseUrl = "https://musicbrainz.org/ws/2/"
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .client(client)
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: MusicBrainApiService by lazy {
        retrofit.create(MusicBrainApiService::class.java)
    }

    override val artistRepository: ArtistRepository by lazy {
        ApiArtistsRepository(MusicDb.getDatabase(context = context).artistDao(), retrofitService)
    }

    override val instrumentRepository: InstrumentRepository by lazy {
        ApiInstrumentsRepository(MusicDb.getDatabase(context = context).instrumentDao(), retrofitService)
    }
}