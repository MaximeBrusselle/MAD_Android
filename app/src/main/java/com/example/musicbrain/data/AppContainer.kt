package com.example.musicbrain.data

import android.content.Context
import com.example.musicbrain.data.database.MusicDb
import com.example.musicbrain.network.MusicBrainApiService
import com.example.musicbrain.network.NetworkConnectionInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Dependency injection container for providing application-level dependencies.
 * This container includes repositories for artists and instruments along with their required dependencies.
 */
interface AppContainer {
    /**
     * Provides the repository for managing artist-related data.
     */
    val artistRepository: ArtistRepository

    /**
     * Provides the repository for managing instrument-related data.
     */
    val instrumentRepository: InstrumentRepository
}

/**
 * Default implementation of the [AppContainer] interface.
 *
 * @param context The application context.
 */
class DefaultAppContainer(private val context: Context) : AppContainer {
    /**
     * The [NetworkConnectionInterceptor] instance for the application.
     */
    private val networkCheck = NetworkConnectionInterceptor(context)

    /**
     * The [OkHttpClient] instance for the application.
     */
    private val client =
        OkHttpClient.Builder()
            .addInterceptor(networkCheck)
            .build()

    /**
     * The base URL for the MusicBrainz API.
     */
    private val baseUrl = "https://musicbrainz.org/ws/2/"

    /**
     * The [Json] instance for the application.
     */
    private val json =
        Json {
            ignoreUnknownKeys = true
        }

    /**
     * The [Retrofit] instance for the application.
     */
    private val retrofit =
        Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .client(client)
            .baseUrl(baseUrl)
            .build()

    /**
     * The [MusicBrainApiService] instance for the application.
     */
    private val retrofitService: MusicBrainApiService by lazy {
        retrofit.create(MusicBrainApiService::class.java)
    }

    /**
     * Provides the repository for managing artist-related data using the [ApiArtistsRepository] implementation.
     */
    override val artistRepository: ArtistRepository by lazy {
        ApiArtistsRepository(MusicDb.getDatabase(context = context).artistDao(), retrofitService)
    }

    /**
     * Provides the repository for managing instrument-related data using the [ApiInstrumentsRepository] implementation.
     */
    override val instrumentRepository: InstrumentRepository by lazy {
        ApiInstrumentsRepository(MusicDb.getDatabase(context = context).instrumentDao(), retrofitService)
    }
}
